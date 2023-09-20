package yongkinanum.backenddeploy.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.*;
import yongkinanum.backenddeploy.core.security.JwtProvider;
import yongkinanum.backenddeploy.post.Post;
import yongkinanum.backenddeploy.post.PostJPARepository;
import yongkinanum.backenddeploy.shop.Shop;
import yongkinanum.backenddeploy.shop.ShopJPARepository;
import yongkinanum.backenddeploy.user.address.Address;
import yongkinanum.backenddeploy.user.address.AddressJPARepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserJPARepository userJPARepository;
    private final ShopJPARepository shopJPARepository;
    private final PostJPARepository postJPARepository;
    private final AddressJPARepository addressJPARepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void regist(UserRequest.RegistDTO registDTO) {
        sameIdCheck(registDTO.getUserId());

        registDTO.setPassword(passwordEncoder.encode(registDTO.getPassword()));
        User user = registDTO.toEntity();

        userJPARepository.save(user);
    }

    private void sameIdCheck(String userId) {
        User findUser = userJPARepository.findByUserId(userId);

        if(findUser != null) {
            throw new Exception409("이미 존재하는 아이디입니다.");
        }
    }

    public String login(UserRequest.LoginDTO loginDTO) {
        User findUser = userJPARepository.findByUserId(loginDTO.getUserId());
        checkUnregistUser(findUser);

        if(findUser == null) {
            throw new Exception400("로그인에 실패하였습니다.");
        }

        if(!findUser.getUserId().equals(loginDTO.getUserId()) || !passwordEncoder.matches(loginDTO.getPassword(), findUser.getPassword())) {
            throw new Exception400("로그인에 실패하였습니다.");
        }

        return JwtProvider.create(findUser);
    }

    // 탈퇴한 유저일 경우 조회 불가
    public UserResponse.FindDTO findUserInfo(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());
        checkUnregistUser(findUser);

        List<Post> posts = postJPARepository.findByUserId(findUser.getUserId());

        return new UserResponse.FindDTO(posts, findUser);
    }

    public UserResponse.FindDTO findSpecificUserInfo(Long idx) {
        User findUser = userJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 유저를 찾을 수 없습니다.")
        );

        List<Post> posts = postJPARepository.findByUserId(findUser.getUserId());

        return new UserResponse.FindDTO(posts, findUser);
    }

    public UserResponse.FindShopDTO findShopInfo(Long idx, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        if(findUser.getIdx() != idx) {
            throw new Exception403("권한이 없습니다.");
        }

        Shop findShop = shopJPARepository.findShopByUserIdx(findUser.getIdx());

        return new UserResponse.FindShopDTO(findShop);
    }

    @Transactional
    public void updateUserInfo(UserRequest.UpdateDTO updateDTO, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());
        checkUnregistUser(findUser);

        if(updateDTO.getOldPassword() != null && !passwordEncoder.matches(updateDTO.getOldPassword(), findUser.getPassword())) {
            throw new Exception401("인증되지 않았습니다.");
        }

        // 변경할 수 있는 정보가 없을 때
        if(updateDTO.getNewNickname() == null && updateDTO.getNewPassword() == null) {
            throw new Exception400("잘못된 요청입니다.");
        }

        // 비밀번호만 변경할 때
        if(updateDTO.getNewNickname() == null && updateDTO.getNewPassword() != null) {
            findUser.updatePassword(passwordEncoder.encode(updateDTO.getNewPassword()));
        }

        // 닉네임만 변경할 때
        if(updateDTO.getNewNickname() != null && updateDTO.getNewPassword() == null) {
            findUser.updateUserName(updateDTO.getNewNickname());
        }

        // 닉네임과 비밀번호 모두 변경할 때
        if(updateDTO.getNewNickname() != null && updateDTO.getNewPassword() != null) {
            findUser.update(updateDTO.getNewNickname(), passwordEncoder.encode(updateDTO.getNewPassword()));
        }
    }

    @Transactional
    public void unregistUser(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());
        checkUnregistUser(findUser);

        findUser.setUnregist('Y');
    }

    private void checkUnregistUser(User user) {
        if(user.getUnregist() == 'Y') {
            throw new Exception404("해당 유저의 정보를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void addUserAddress(UserRequest.AddAddressDTO addAddressDTO, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        Address address = Address.builder()
                .address(addAddressDTO.getAddress())
                .user(findUser)
                .build();

        addressJPARepository.save(address);
    }

    public UserResponse.FindAddressDTO findUserAddresses(User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        List<Address> addresses = addressJPARepository.findAllAddressByUserIdx(findUser.getIdx());

        return new UserResponse.FindAddressDTO(addresses);
    }
}
