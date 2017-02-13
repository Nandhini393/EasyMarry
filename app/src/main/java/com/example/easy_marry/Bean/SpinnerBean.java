package com.example.easy_marry.Bean;

/**
 * Created by android01 on 21/10/16.
 */
public class SpinnerBean {
    private String category_id;
    private String category_name;

    private String city_id;
    private String city_name;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }


    public String get_categoryId() {
        return category_id;
    }

    public void set_categoryId(String category_id) {
        this.category_id = category_id;
    }


    public void set_categoryName(String category_name) {
        this.category_name = category_name;
    }

    public String getcategoryName() {
        return this.category_name;
    }


}
