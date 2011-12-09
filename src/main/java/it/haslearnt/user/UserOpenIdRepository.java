package it.haslearnt.user;

import it.haslearnt.cassandra.mapping.CassandraRepository;

import org.springframework.stereotype.Repository;

@Repository
public class UserOpenIdRepository extends CassandraRepository<UserOpenId> {
}
