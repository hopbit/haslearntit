/*
 * Copyright (c) (2005 - 2011) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package it.haslearnt.controller.registration.form;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

class UserRegistrationForm {
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    @NotNull
    @Size(min = 3)
    private String email;

    @NotNull
    @Size(min = 8, max = 100)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
