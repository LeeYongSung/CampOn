package com.camp.campon.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.camp.campon.dto.Product;
import com.camp.campon.mapper.ProductMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public int productInsert(Product product) throws Exception {
        // * 썸네일 이미지
         List<MultipartFile> productThmFile =  product.getProductThmFile();
        if( !productThmFile.isEmpty() )
        for (MultipartFile file : productThmFile) {
        if( file.isEmpty() ) continue;
        String fileName = UUID.randomUUID().toString()+"_"+ file.getOriginalFilename();
        String filepath = uploadPath+"/"+fileName;
        File uploadfile = new File(uploadPath, fileName);
        byte[] filedata = file.getBytes();
        FileCopyUtils.copy(filedata, uploadfile);
        product.setProductThumnail(filepath);
        }
        // * 상품 상세 설명
        List<MultipartFile> productConFile =  product.getProductConFile();
        if( !productConFile.isEmpty() )
        for (MultipartFile file : productConFile) {
        if( file.isEmpty() ) continue;
        String fileName = UUID.randomUUID().toString()+"_"+ file.getOriginalFilename();
        String filepath = uploadPath+"/"+fileName;
        File uploadfile = new File(uploadPath, fileName);
        byte[] filedata = file.getBytes();
        FileCopyUtils.copy(filedata, uploadfile);
        product.setProductCon(filepath);
        }
        //product 테이블에 등록
        int result = productMapper.productInsert(product);
        log.info("상품등록여부 : "+result);
        int productMaxNo = productMapper.maxPk(); 
        log.info("상품번호 최댓값 : "+productMaxNo);
        product.setProductNo(productMaxNo);
        // * 상세이미지들
        List<MultipartFile> productImgs = product.getProductImgs();
        if( !productImgs.isEmpty() )
        for (MultipartFile file : productImgs) {
            if( file.isEmpty() ) continue;
            String originName = file.getOriginalFilename();
            String fileName = UUID.randomUUID().toString() + "_" + originName;
            String filePath = uploadPath + "/" + fileName;
            File uploadFile = new File(uploadPath, fileName);
            byte[] fileData = file.getBytes();
            FileCopyUtils.copy(fileData, uploadFile);
            //productimg 테이블에 정보 넣기
            product.setProductimgUrl(filePath); //productimgNo는 사실상 productNo와 같음. 
            productMapper.insertImgs(product);
        }
       
        return result;
    }

    @Override
    public List<Product> getCategoryList(String category) throws Exception {
        List<Product> productList = productMapper.getCategoryList(category);
        return productList;
    }
    
}
