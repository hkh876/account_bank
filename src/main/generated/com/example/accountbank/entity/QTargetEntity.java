package com.example.accountbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTargetEntity is a Querydsl query type for TargetEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTargetEntity extends EntityPathBase<TargetEntity> {

    private static final long serialVersionUID = -1192274519L;

    public static final QTargetEntity targetEntity = new QTargetEntity("targetEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final StringPath targetIcon = createString("targetIcon");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QTargetEntity(String variable) {
        super(TargetEntity.class, forVariable(variable));
    }

    public QTargetEntity(Path<? extends TargetEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTargetEntity(PathMetadata metadata) {
        super(TargetEntity.class, metadata);
    }

}

