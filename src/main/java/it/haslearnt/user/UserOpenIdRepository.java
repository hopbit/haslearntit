/*
 * Copyright (c) (2005 - 2011) TouK sp. z o.o. s.k.a.
 * All rights reserved
 */

package it.haslearnt.user;

import it.haslearnt.cassandra.mapping.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserOpenIdRepository extends CassandraRepository<UserOpenId> {
}
