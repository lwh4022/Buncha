package com.buncha.model.freelancer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreelancer is a Querydsl query type for Freelancer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFreelancer extends EntityPathBase<Freelancer> {

    private static final long serialVersionUID = -1677981367L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreelancer freelancer = new QFreelancer("freelancer");

    public final StringPath address = createString("address");

    public final StringPath birthday = createString("birthday");

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final StringPath cphone = createString("cphone");

    public final SimplePath<java.sql.Clob> description = createSimple("description", java.sql.Clob.class);

    public final NumberPath<Long> freelancerSeq = createNumber("freelancerSeq", Long.class);

    public final EnumPath<com.buncha.model.enumClass.GenderType> genderType = createEnum("genderType", com.buncha.model.enumClass.GenderType.class);

    public final com.buncha.model.member.QMember member;

    public final StringPath vnName = createString("vnName");

    public QFreelancer(String variable) {
        this(Freelancer.class, forVariable(variable), INITS);
    }

    public QFreelancer(Path<? extends Freelancer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreelancer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreelancer(PathMetadata metadata, PathInits inits) {
        this(Freelancer.class, metadata, inits);
    }

    public QFreelancer(Class<? extends Freelancer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.buncha.model.member.QMember(forProperty("member")) : null;
    }

}

