package com.example.accountbank.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBudgetEntity is a Querydsl query type for BudgetEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBudgetEntity extends EntityPathBase<BudgetEntity> {

    private static final long serialVersionUID = 1951104093L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBudgetEntity budgetEntity = new QBudgetEntity("budgetEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCategoryEntity category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> money = createNumber("money", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QBudgetEntity(String variable) {
        this(BudgetEntity.class, forVariable(variable), INITS);
    }

    public QBudgetEntity(Path<? extends BudgetEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBudgetEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBudgetEntity(PathMetadata metadata, PathInits inits) {
        this(BudgetEntity.class, metadata, inits);
    }

    public QBudgetEntity(Class<? extends BudgetEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategoryEntity(forProperty("category")) : null;
    }

}

