package com.buncha.model.freelancer;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFreelancerFile is a Querydsl query type for FreelancerFile
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFreelancerFile extends EntityPathBase<FreelancerFile> {

    private static final long serialVersionUID = -1057642907L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFreelancerFile freelancerFile = new QFreelancerFile("freelancerFile");

    public final DateTimePath<java.util.Date> changed = createDateTime("changed", java.util.Date.class);

    public final StringPath fileDownloadUri = createString("fileDownloadUri");

    public final StringPath filename = createString("filename");

    public final NumberPath<Long> fileSeq = createNumber("fileSeq", Long.class);

    public final StringPath fileType = createString("fileType");

    public final QFreelancer freelancer;

    public final NumberPath<Long> size = createNumber("size", Long.class);

    public QFreelancerFile(String variable) {
        this(FreelancerFile.class, forVariable(variable), INITS);
    }

    public QFreelancerFile(Path<? extends FreelancerFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFreelancerFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFreelancerFile(PathMetadata metadata, PathInits inits) {
        this(FreelancerFile.class, metadata, inits);
    }

    public QFreelancerFile(Class<? extends FreelancerFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.freelancer = inits.isInitialized("freelancer") ? new QFreelancer(forProperty("freelancer"), inits.get("freelancer")) : null;
    }

}

