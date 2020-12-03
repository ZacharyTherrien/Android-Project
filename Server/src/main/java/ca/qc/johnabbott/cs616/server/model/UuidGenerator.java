package ca.qc.johnabbott.cs616.server.model;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.UUID;

/**
 * UUID generator for key generation.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class UuidGenerator implements IdentifierGenerator {

    public static final String generatorName = "UuidGenerator";
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return UUID.randomUUID().toString();
    }
}
