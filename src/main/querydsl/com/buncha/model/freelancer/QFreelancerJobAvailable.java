package com.buncha.model.freelancer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreelancerJobAvailable is a Querydsl query type for FreelancerJobAvailable
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFreelancerJobAvailable extends EntityPathBase<FreelancerJobAvailable> {

    private static final long serialVersionUID = -1192776843L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreelancerJobAvailable freelancerJobAvailable = new QFreelancerJobAvailable("freelancerJobAvailable");

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final QFreelancer freelancer;

    public final EnumPath<com.buncha.model.enumClass.CareerType> jobAvailable = createEnum("jobAvailable", com.buncha.model.enumClass.CareerType.class);

    public final NumberPath<Long> jobAvailableSeq = createNumber("jobAvailableSeq", Long.class);

    public QFreelancerJobAvailable(String variable) {
        this(FreelancerJobAvailable.class, forVariable(variable), INITS);
    }

    public QFreelancerJobAvailable(Path<? extends FreelancerJobAvailable> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreelancerJobAvailable(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreelancerJobAvailable(PathMetadata metadata, PathInits inits) {
        this(FreelancerJobAvailable.class, metadata, inits);
    }

    public QFreelancerJobAvailable(Class<? extends FreelancerJobAvailable> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freelancer = inits.isInitialized("freelancer") ? new QFreelancer(forProperty("freelancer"), inits.get("freelancer")) : null;
    }

}

