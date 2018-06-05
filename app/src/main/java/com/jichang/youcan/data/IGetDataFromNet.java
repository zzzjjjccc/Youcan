package com.jichang.youcan.data;

import com.jichang.youcan.data.bean.req.Register;
import com.jichang.youcan.data.bean.req.Share;
import com.jichang.youcan.data.callback.IYoucanDataResponse;
import com.jichang.youcan.db.model.Content;

/**
 * @author jichang
 * @date 2018/1/24
 * email: 2218982471@qq.com
 * describ: Common interface for outside call
 */

public interface IGetDataFromNet {

    /**
     * Register Youcan
     *
     * @param register User info
     * @param response Callback
     */
    void register(Register register, IYoucanDataResponse<String> response);

    /**
     * Register Youcan
     *
     * @param register User info
     * @param response Callback
     */
    void login(Register register, IYoucanDataResponse<String> response);

    /**
     * Share to others
     *
     * @param share    Data of Shared
     * @param response Callback
     */
    void share(Share share, IYoucanDataResponse<String> response);

    /**
     * Get users to add
     *
     * @param response Callback
     */
    void getUsers(IYoucanDataResponse<String> response);

    /**
     * Get user's detail information
     * param phone number
     *
     * @param response Response
     */
    void getUser(String phone, IYoucanDataResponse<String> response);

    /**
     * Add User Detail
     *
     * @param school   school
     * @param major    major
     * @param name     name
     * @param sex      sex
     * @param response response
     */
    void addUserDetail(String phone,
                       String school,
                       String major,
                       String name,
                       String sex,
                       IYoucanDataResponse<String> response);

    /**
     * Add idol and fan through this method
     *
     * @param phone    Local user
     * @param idol     Someone you want to subscribe, and become he's fan
     * @param response Response
     */
    void addSubscribe(String phone, String idol, IYoucanDataResponse<String> response);

    /**
     * Judge if given phone number is your idol
     *
     * @param phone    Phone number
     * @param idol     idol number
     * @param response Response
     */
    void isIdol(String phone, String idol, IYoucanDataResponse<String> response);

    /**
     * Get Idols from net according to you given phone number
     *
     * @param phone    Phone number
     * @param response Response
     */
    void getIdols(String phone, IYoucanDataResponse<String> response);

    /**
     * Get Fans from net according to you given phone number
     *
     * @param phone    Phone number
     * @param response Response
     */
    void getFans(String phone, IYoucanDataResponse<String> response);


    /**
     * Add a content
     *
     * @param phone    Phone number
     * @param content  Content
     * @param response Response
     */
    void addContent(String phone, Content content, IYoucanDataResponse<String> response);

    /**
     * Share a content to other person
     *
     * @param phone    The phone number of your self
     * @param date     Date
     * @param fan      Your fan's number
     * @param response Response
     */
    void share(String phone, String date, String fan, IYoucanDataResponse response);

    /**
     * Discover someone your idol content
     *
     * @param phone    Phone number
     * @param response Response
     */
    void discover(String phone, IYoucanDataResponse response);

}
