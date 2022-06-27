package org.myddd.extesions.media.gridfs;

import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.myddd.extesions.media.MediaNotFoundException;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

@Named
public class GridFSRepository {

    @Inject
    private GridFsTemplate template;

    @Inject
    private GridFsOperations operations;


    public String uploadToStorage(String fileName,InputStream inputStream) {
        Object fileID = template.store(inputStream, fileName);
        return fileID.toString();
    }

    public InputStream downloadFromStorage(String mediaId) {
        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(new ObjectId(mediaId))) );

        if(Objects.isNull(gridFSFile)){
            throw new MediaNotFoundException();
        }

        try {
            return operations.getResource(gridFSFile).getInputStream();
        } catch (IOException e) {
            throw new MediaNotFoundException();
        }
    }

}
