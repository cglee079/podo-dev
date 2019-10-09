package com.cglee079.pododev.web.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUpdatableBaseEntity is a Querydsl query type for UpdatableBaseEntity
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QUpdatableBaseEntity extends EntityPathBase<UpdatableBaseEntity> {

    private static final long serialVersionUID = -855684588L;

    public static final QUpdatableBaseEntity updatableBaseEntity = new QUpdatableBaseEntity("updatableBaseEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createAt = _super.createAt;

    //inherited
    public final StringPath createBy = _super.createBy;

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public final StringPath updateBy = createString("updateBy");

    public QUpdatableBaseEntity(String variable) {
        super(UpdatableBaseEntity.class, forVariable(variable));
    }

    public QUpdatableBaseEntity(Path<? extends UpdatableBaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUpdatableBaseEntity(PathMetadata metadata) {
        super(UpdatableBaseEntity.class, metadata);
    }

}

