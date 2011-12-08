/*
 * Copyright (c) (2005 - 2011) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package it.haslearnt.user;

import it.haslearnt.cassandra.mapping.Column;
import it.haslearnt.cassandra.mapping.Entity;
import it.haslearnt.cassandra.mapping.Id;

@Entity("UserOpenId")
public class UserOpenId {
    @Id
	private String openIdLogin;

    @Column
    private String name;

    public UserOpenId() {
    }

    public UserOpenId(String openIdLogin) {
        this.openIdLogin = openIdLogin;
    }

    public String openIdLogin() {
        return openIdLogin;
    }

    public String name() {
        return name;
    }

    public UserOpenId withName(String name) {
        this.name = name;
        return this;
    }

}
