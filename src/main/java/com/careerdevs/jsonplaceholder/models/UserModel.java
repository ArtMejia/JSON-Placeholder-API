package com.careerdevs.jsonplaceholder.models;

import com.fasterxml.jackson.annotation.JsonInclude;

public class UserModel {

    private int id;
    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;

    private UserCompany company;

    public class UserCompany {
        private String name;
        private String catchPhrase;
        private String bs;

        public String getName() {
            return name;
        }

        public String getCatchPhrase() {
            return catchPhrase;
        }

        public String getBs() {
            return bs;
        }
    }

    private UserAddress address;

    public class UserAddress {

        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private UserGeo geo;

        public String getStreet() {
            return street;
        }

        public String getSuite() {
            return suite;
        }

        public String getCity() {
            return city;
        }

        public String getZipcode() {
            return zipcode;
        }

        public UserGeo getGeo() {
            return geo;
        }

            public class UserGeo {

                private String lat;
                private String lng;

                public String getLat() {
                    return lat;
                }

                public String getLng() {
                    return lng;
                }
        }
    }


    // @JsonInclude(JsonInclude.Include.NON_NULL)


    public UserAddress getAddress() {
        return address;
    }

    public UserCompany getCompany() {
        return company;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }
}
