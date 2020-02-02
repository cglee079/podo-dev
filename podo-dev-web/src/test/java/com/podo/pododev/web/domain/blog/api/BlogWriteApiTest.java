//package com.podo.pododev.web.domain.blog.api;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.podo.pododev.util.TestUtil;
//import com.podo.pododev.web.domain.blog.BlogDto;
//import com.podo.pododev.web.domain.blog.attachfile.AttachFileStorageUploader;
//import com.podo.pododev.web.domain.blog.attachimage.AttachImageStorageUploader;
//import com.podo.pododev.web.domain.blog.repository.BlogRepository;
//import com.podo.pododev.web.domain.blog.service.BlogWriteService;
//import com.podo.pododev.web.global.util.AttachLinkManager;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.then;
//import static org.mockito.Mockito.times;
//
//@ExtendWith(MockitoExtension.class)
//class BlogWriteApiTest {
//
//    @Mock
//    private AttachLinkManager linkManager;
//
//    @Mock
//    private AttachImageStorageUploader attachImageStorageUploader;
//
//    @Mock
//    private AttachFileStorageUploader attachFileStorageUploader;
//
//    @InjectMocks
//    private BlogWriteService blogWriteService;
//
//
//    @Test
//    void testInsertNewBlog01() throws JsonProcessingException {
//
//        //given
//        final ObjectMapper objectMapper = new ObjectMapper();
//
//        final String data = TestUtil.getStringFromResource("data", "sample_insert_blog.json");
//        final BlogDto.insert insert = objectMapper.readValue(data, BlogDto.insert.class);
//
//        //when
//        blogWriteService.insertNewBlog(insert);
//
//        //then
//        then(attachImageStorageUploader).should(times(1)).writeFileOfAttachImagesToStorage(any());
//        then(attachFileStorageUploader).should(times(1)).writeFileOfAttachFilesToStorage(any());
//
//    }
//
//}