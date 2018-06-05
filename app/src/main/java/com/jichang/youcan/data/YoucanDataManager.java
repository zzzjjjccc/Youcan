package com.jichang.youcan.data;

import com.jichang.youcan.data.bean.req.Register;
import com.jichang.youcan.data.bean.req.Share;
import com.jichang.youcan.data.callback.IYoucanDataResponse;
import com.jichang.youcan.data.impl.GetDataFromNetSingleton;
import com.jichang.youcan.db.model.Content;

/**
 * @author jichang
 * @date 2018/1/24
 * email: 2218982471@qq.com
 * describ: Data manager of Youcan
 */

public class YoucanDataManager {

    private IGetDataFromNet holder = GetDataFromNetSingleton.getInstance();

    private YoucanDataManager() {
    }

    private static class SingletonHolder {
        private static final YoucanDataManager INSTANCE = new YoucanDataManager();

    }

    public static YoucanDataManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void register(Register register, IYoucanDataResponse<String> response) {
        holder.register(register, response);
    }

    public void login(Register register, IYoucanDataResponse<String> response) {
        holder.login(register, response);
    }

    public void share(Share share, IYoucanDataResponse<String> response) {
        holder.share(share, response);
    }

    public void getUsers(IYoucanDataResponse<String> response) {
        holder.getUsers(response);
    }

    public void getUser(String phone, IYoucanDataResponse<String> response) {
        holder.getUser(phone, response);
    }

    public void addUserDetail(String phone,
                              String school,
                              String major,
                              String name,
                              String sex,
                              IYoucanDataResponse<String> response) {
        holder.addUserDetail(phone, school, major, name, sex, response);
    }

    public void addSubscribe(String phone, String idol, final IYoucanDataResponse<String> response) {
        holder.addSubscribe(phone, idol, response);
    }

    public void isIdol(String phone, String idol, final IYoucanDataResponse<String> response) {
        holder.isIdol(phone, idol, response);
    }

    public void getIdols(String phone, final IYoucanDataResponse<String> response) {
        holder.getIdols(phone, response);
    }

    public void getFans(String phone, final IYoucanDataResponse<String> response) {
        holder.getFans(phone, response);
    }

    public void addContent(String phone, Content content, final IYoucanDataResponse<String> response) {
        holder.addContent(phone, content, response);
    }

    public void share(String phone, String date, String fan, final IYoucanDataResponse<String> response) {
        holder.share(phone, date, fan, response);
    }

    public void discover(String phone, final IYoucanDataResponse<String> response) {
        holder.discover(phone, response);
    }
}
