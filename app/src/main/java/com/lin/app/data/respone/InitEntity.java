package com.lin.app.data.respone;

import com.yeyuanyuan.web.BaseRequetResult;

import java.util.List;

/**
 * Created by linhui on 2017/12/6.
 */
public class InitEntity extends BaseRequetResult{


    /**
     * result : true
     * code : 10000
     * msg : 获取数据成功
     * advertisting : [{"list":[{"adQueue":["GDT","TT","XF"]}],"locationName":"开屏","adver_location_id":"1","isOnly":true},{"list":[{"adQueue":["GDT","TT","XF"]}],"locationName":"本地信息流","adver_location_id":"13","isOnly":true},{"list":[{"adQueue":["GDT","TT","XF"]}],"locationName":"云端信息流","adver_location_id":"14","isOnly":true},{"list":[{"adQueue":["GDT","TT","XF"]}],"locationName":"截图信息流","adver_location_id":"15","isOnly":true},{"list":[{"adQueue":["GDT","TT","XF"]}],"locationName":"个人中心","adver_location_id":"22","isOnly":true},{"list":[{"adQueue":["GDT","TT","XF"]}],"locationName":"广场","adver_location_id":"23","isOnly":true}]
     */

    private boolean result;
    private int code;
    private String msg;
    private List<AdvertistingBean> advertisting;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<AdvertistingBean> getAdvertisting() {
        return advertisting;
    }

    public void setAdvertisting(List<AdvertistingBean> advertisting) {
        this.advertisting = advertisting;
    }

    public static class AdvertistingBean {
        /**
         * list : [{"adQueue":["GDT","TT","XF"]}]
         * locationName : 开屏
         * adver_location_id : 1
         * isOnly : true
         */

        private String locationName;
        private String adver_location_id;
        private boolean isOnly;
        private List<ListBean> list;

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getAdver_location_id() {
            return adver_location_id;
        }

        public void setAdver_location_id(String adver_location_id) {
            this.adver_location_id = adver_location_id;
        }

        public boolean isIsOnly() {
            return isOnly;
        }

        public void setIsOnly(boolean isOnly) {
            this.isOnly = isOnly;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            private List<String> adQueue;

            public List<String> getAdQueue() {
                return adQueue;
            }

            public void setAdQueue(List<String> adQueue) {
                this.adQueue = adQueue;
            }
        }
    }

    @Override
    public String toString() {
        return "InitEntity{" +
                "result=" + result +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", advertisting=" + advertisting +
                '}';
    }
}
