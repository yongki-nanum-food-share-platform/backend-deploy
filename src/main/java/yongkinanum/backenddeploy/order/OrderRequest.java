package yongkinanum.backenddeploy.order;

import lombok.Getter;
import lombok.Setter;

public class OrderRequest {
    @Getter
    @Setter
    public static class InfoDTO {
        private Long postIdx;
    }
}
