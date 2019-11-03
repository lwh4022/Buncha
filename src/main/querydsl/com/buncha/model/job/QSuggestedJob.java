package com.buncha.model.job;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSuggestedJob is a Querydsl query type for SuggestedJob
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSuggestedJob extends EntityPathBase<SuggestedJob> {

    private static final long serialVersionUID = -108903152L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSuggestedJob suggestedJob = new QSuggestedJob("suggestedJob");

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final com.buncha.model.freelancer.QFreelancer freelancer;

    public final QJob job;

    public final NumberPath<Long> suggestedJobSeq = createNumber("suggestedJobSeq", Long.class);

    public QSuggestedJob(String variable) {
        this(SuggestedJob.class, forVariable(variable), INITS);
    }

    public QSuggestedJob(Path<? extends SuggestedJob> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSuggestedJob(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSuggestedJob(PathMetadata metadata, PathInits inits) {
        this(SuggestedJob.class, metadata, inits);
    }

    public QSuggestedJob(Class<? extends SuggestedJob> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freelancer = inits.isInitialized("freelancer") ? new com.buncha.model.freelancer.QFreelancer(forProperty("freelancer"), inits.get("freelancer")) : null;
        this.job = inits.isInitialized("job") ? new QJob(forProperty("job"), inits.get("job")) : null;
    }

}

