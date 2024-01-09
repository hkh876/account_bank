package com.example.accountbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPersistentLoginEntity is a Querydsl query type for PersistentLoginEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPersistentLoginEntity extends EntityPathBase<PersistentLoginEntity> {

    private static final long serialVersionUID = -2041443232L;

    public static final QPersistentLoginEntity persistentLoginEntity = new QPersistentLoginEntity("persistentLoginEntity");

    public final DateTimePath<java.time.LocalDateTime> lastUsed = createDateTime("lastUsed", java.time.LocalDateTime.class);

    public final StringPath series = createString("series");

    public final StringPath token = createString("token");

    public final StringPath username = createString("username");

    public QPersistentLoginEntity(String variable) {
        super(PersistentLoginEntity.class, forVariable(variable));
    }

    public QPersistentLoginEntity(Path<? extends PersistentLoginEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPersistentLoginEntity(PathMetadata metadata) {
        super(PersistentLoginEntity.class, metadata);
    }

}

