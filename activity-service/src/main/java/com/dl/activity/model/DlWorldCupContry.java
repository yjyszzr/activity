package com.dl.activity.model;

import javax.persistence.*;

@Table(name = "dl_world_cup_contry")
public class DlWorldCupContry {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 国家id
     */
    @Column(name = "country_id")
    private Integer countryId;

    /**
     * 联赛国家名称
     */
    @Column(name = "contry_name")
    private String contryName;

    /**
     * 联赛国家图标
     */
    @Column(name = "contry_pic")
    private String contryPic;

    /**
     * 是否16强,1-true,0-false
     */
    @Column(name = "is_16")
    private Integer is16;

    /**
     * 是否8强,1-true,0-false
     */
    @Column(name = "is_8")
    private Integer is8;

    /**
     * 是否4强,1-true,0-false
     */
    @Column(name = "is_4")
    private Integer is4;

    /**
     * 是否冠亚军,1-true,0-false
     */
    @Column(name = "is_2")
    private Integer is2;

    /**
     * 是否冠军,1-true,0-false
     */
    @Column(name = "is_1")
    private Integer is1;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取国家id
     *
     * @return country_id - 国家id
     */
    public Integer getCountryId() {
        return countryId;
    }

    /**
     * 设置国家id
     *
     * @param countryId 国家id
     */
    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    /**
     * 获取联赛国家名称
     *
     * @return contry_name - 联赛国家名称
     */
    public String getContryName() {
        return contryName;
    }

    /**
     * 设置联赛国家名称
     *
     * @param contryName 联赛国家名称
     */
    public void setContryName(String contryName) {
        this.contryName = contryName;
    }

    /**
     * 获取联赛国家图标
     *
     * @return contry_pic - 联赛国家图标
     */
    public String getContryPic() {
        return contryPic;
    }

    /**
     * 设置联赛国家图标
     *
     * @param contryPic 联赛国家图标
     */
    public void setContryPic(String contryPic) {
        this.contryPic = contryPic;
    }

    /**
     * 获取是否16强,1-true,0-false
     *
     * @return is_16 - 是否16强,1-true,0-false
     */
    public Integer getIs16() {
        return is16;
    }

    /**
     * 设置是否16强,1-true,0-false
     *
     * @param is16 是否16强,1-true,0-false
     */
    public void setIs16(Integer is16) {
        this.is16 = is16;
    }

    /**
     * 获取是否8强,1-true,0-false
     *
     * @return is_8 - 是否8强,1-true,0-false
     */
    public Integer getIs8() {
        return is8;
    }

    /**
     * 设置是否8强,1-true,0-false
     *
     * @param is8 是否8强,1-true,0-false
     */
    public void setIs8(Integer is8) {
        this.is8 = is8;
    }

    /**
     * 获取是否4强,1-true,0-false
     *
     * @return is_4 - 是否4强,1-true,0-false
     */
    public Integer getIs4() {
        return is4;
    }

    /**
     * 设置是否4强,1-true,0-false
     *
     * @param is4 是否4强,1-true,0-false
     */
    public void setIs4(Integer is4) {
        this.is4 = is4;
    }

    /**
     * 获取是否冠亚军,1-true,0-false
     *
     * @return is_2 - 是否冠亚军,1-true,0-false
     */
    public Integer getIs2() {
        return is2;
    }

    /**
     * 设置是否冠亚军,1-true,0-false
     *
     * @param is2 是否冠亚军,1-true,0-false
     */
    public void setIs2(Integer is2) {
        this.is2 = is2;
    }

    /**
     * 获取是否冠军,1-true,0-false
     *
     * @return is_1 - 是否冠军,1-true,0-false
     */
    public Integer getIs1() {
        return is1;
    }

    /**
     * 设置是否冠军,1-true,0-false
     *
     * @param is1 是否冠军,1-true,0-false
     */
    public void setIs1(Integer is1) {
        this.is1 = is1;
    }
}