package yongkinanum.backenddeploy.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yongkinanum.backenddeploy.core.error.exception.Exception403;
import yongkinanum.backenddeploy.core.error.exception.Exception404;
import yongkinanum.backenddeploy.menu.Menu;
import yongkinanum.backenddeploy.menu.MenuJPARepository;
import yongkinanum.backenddeploy.menu.option.Option;
import yongkinanum.backenddeploy.menu.option.OptionJPARepository;
import yongkinanum.backenddeploy.shop.brand.Brand;
import yongkinanum.backenddeploy.shop.brand.BrandJPARepository;
import yongkinanum.backenddeploy.review.Review;
import yongkinanum.backenddeploy.review.ReviewJPARepository;
import yongkinanum.backenddeploy.user.User;
import yongkinanum.backenddeploy.user.UserJPARepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShopService {
    private final ShopJPARepository shopJPARepository;
    private final OptionJPARepository optionJPARepository;
    private final UserJPARepository userJPARepository;
    private final BrandJPARepository brandJPARepository;
    private final ReviewJPARepository reviewJPARepository;

    @Transactional
    public void registShop(ShopRequest.RegistDTO registDTO, User user) {
        User findUser = userJPARepository.findByUserId(user.getUserId());
        Brand findBrand = brandJPARepository.findByBrandName(registDTO.getBrandName());

        Shop shop = registDTO.toEntity(findUser, findBrand);

        shopJPARepository.save(shop);
    }

    public ShopResponse.FindAllDTO findAllShops(ShopRequest.FindAllDTO findAllDTO) {
        List<Shop> findShops = shopJPARepository.findAllShopByBrandName(findAllDTO.getMenuName());

        return new ShopResponse.FindAllDTO(findShops);
    }

    public ShopResponse.FindDTO findShop(Long idx) {
        Shop findShop = shopJPARepository.findById(idx).orElseThrow(
                () -> new Exception404("해당 가게를 찾을 수 없습니다.")
        );
        checkUnregistShop(findShop);

        List<Option> findOptions = optionJPARepository.findAllOptionByBrandIdx(findShop.getBrand().getIdx());

        List<Review> findReviews =  reviewJPARepository.findByShopId(idx);

        return new ShopResponse.FindDTO(findReviews, findOptions, findShop);
    }

    @Transactional
    public void updateShopInfo(ShopRequest.UpdateDTO updateDTO, User user) {
        Shop findShop = shopJPARepository.findById(updateDTO.getIdx()).orElseThrow(
                () -> new Exception404("해당 가게를 찾을 수 없습니다.")
        );
        checkUnregistShop(findShop);

        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        if(findUser != findShop.getUser()) {
            throw new Exception403("권한이 없습니다.");
        }

        findShop.setShopName(updateDTO.getNewName());
        findShop.setShopAddress(updateDTO.getNewAddress());
        findShop.setTip(Integer.parseInt(updateDTO.getNewTip()));
    }

    private void checkUnregistShop(Shop shop) {
        if(shop.getUnregist() == 'Y') {
            throw new Exception404("해당 가게를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void unregistShop(ShopRequest.UnregistDTO unregistDTO, User user) {
        Shop findShop = shopJPARepository.findById(unregistDTO.getIdx()).orElseThrow(
                () -> new Exception404("해당 가게를 찾을 수 없습니다.")
        );
        checkUnregistShop(findShop);

        User findUser = userJPARepository.findByUserId(user.getUserId());

        findUser.findUserNullCheck(findUser);

        if(findUser != findShop.getUser()) {
            throw new Exception403("권한이 없습니다.");
        }

        findShop.setUnregist('Y');
    }
}
