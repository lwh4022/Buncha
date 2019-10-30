package com.buncha.model.freelancer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreelancerCareer is a Querydsl query type for FreelancerCareer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFreelancerCareer extends EntityPathBase<FreelancerCareer> {

    private static final long serialVersionUID = 1419321895L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreelancerCareer freelancerCareer = new QFreelancerCareer("freelancerCareer");

    public final NumberPath<Long> careerSeq = createNumber("careerSeq", Long.class);

    public final EnumPath<com.buncha.model.enumClass.CareerType> careerType = createEnum("careerType", com.buncha.model.enumClass.CareerType.class);

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final StringPath company = createString("company");

    public final StringPath endDate = createString("endDate");

    public final QFreelancer freelancer;

    public final StringPath job = createString("job");

    public final StringPath startDate = createString("startDate");

    public QFreelancerCareer(String variable) {
        this(FreelancerCareer.class, forVariable(variable), INITS);
    }

    public QFreelancerCareer(Path<? extends FreelancerCareer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreelancerCareer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreelancerCareer(PathMetadata metadata, PathInits inits) {
        this(FreelancerCareer.class, metadata, inits);
    }

    public QFreelancerCareer(Class<? extends FreelancerCareer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freelancer = inits.isInitialized("freelancer") ? new QFreelancer(forProperty("freelancer"), inits.get("freelancer")) : null;
    }

}

