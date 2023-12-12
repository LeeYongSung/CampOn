package com.camp.campon.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.camp.campon.dto.Camp;
import com.camp.campon.mapper.CampMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CampServiceImpl implements CampService{

    @Autowired
    private CampMapper campMapper;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public List<Camp> newList() throws Exception{
        List<Camp> campnewList = campMapper.newList();
        return campnewList;
    }

    @Override
    public List<Camp> favoritesList() throws Exception {
        List<Camp> favoritesList = campMapper.favoritesList();
        return favoritesList;
    }

    @Override
    public List<Camp> campSelect(int campTypeNo) throws Exception {
        List<Camp> campselect = campMapper.campSelect(campTypeNo);
        // log.info("campTypeNo : " + campselect);
        return campselect;
    }
    
    @Override
    public List<Camp> hotList() throws Exception {
        List<Camp> campHotList = campMapper.hotList();
        return campHotList;    
    }
    
    @Override
    public List<Camp> camptype() throws Exception {
        List<Camp> camptypeList = campMapper.camptype();
        
        return camptypeList;
    }
    
    @Override
    public int favoriteDelete(int favoritesNo) throws Exception {
        int result = campMapper.favoriteDelete(favoritesNo);
        return result;
    }
    
    
    // 캠핑장 페이지
    @Override
    public List<Camp> productsimg(int campNo) throws Exception {
        List<Camp> productsimg = campMapper.productsimg(campNo);
        return productsimg;
    }
    
    @Override
    public Camp productsproducts(int campNo) throws Exception {
        Camp productsproducts = campMapper.productsproducts(campNo);
        return productsproducts;
    }
    
    @Override
    public int productsreserve(int campNo) throws Exception {
        int productsreserve = campMapper.productsreserve(campNo);
        return productsreserve;
    }
    @Override
    public List<Camp> productsenvironment(int campNo) throws Exception {
        List<Camp> productsenvironment = campMapper.productsenvironment(campNo);
        return productsenvironment;
    }
    @Override
    public List<Camp> productsfacility(int campNo) throws Exception {
        List<Camp> productsfacility = campMapper.productsfacility(campNo);
        return productsfacility;
    }
    @Override
    public List<Camp> productsproductlist(int campNo) throws Exception {
        List<Camp> productsproductlist = campMapper.productsproductlist(campNo);
        return productsproductlist;
    }
    
    
    // 예약
    public List<Camp> reservation(Integer userNo) throws Exception {
        List<Camp> reservationList = campMapper.reservation(userNo);
        
        return reservationList;
    }
    
    @Override
    public List<Camp> schedule(Camp camp) throws Exception {
        List<Camp> scheduleList = campMapper.schedule(camp);

        return scheduleList;

    }

    @Override
    public List<Camp> productimg(int cpdtNo) throws Exception {
        List<Camp> productimg = campMapper.productimg(cpdtNo);
        return productimg;
    }

    @Override
    public Camp productintro(int cpdtNo) throws Exception {
        Camp productintro = campMapper.productintro(cpdtNo);
        return productintro;
    }

    @Override
    public Camp reservate(int cpdtNo) throws Exception {
        Camp camp = campMapper.reservate(cpdtNo);

        return camp;
    }
    @Override
    public Camp reservecomplete(String userId) throws Exception{
        Camp reservecomplete = campMapper.reservecomplete(userId);
        return reservecomplete;
    }

    //캠핑상품 등록
    @Override
    public int detailinsert(Camp camp) throws Exception{
        int result = campMapper.detailinsert(camp);
        int campNo = camp.getCampNo();
        int cpdtNo = campMapper.maxdetailNo();

        List<MultipartFile> fileList = camp.getCpdiFiles();

        if(!fileList.isEmpty())
        for(MultipartFile file : fileList){
            if(file.isEmpty()) continue;
            String originName = file.getOriginalFilename();
            long fileSize = file.getSize();
            byte[] fileData = file.getBytes();

            String fileName = UUID.randomUUID().toString() + "_" + originName;
            String filePath = uploadPath + "/" + fileName;

            File uploadFile = new File(uploadPath, fileName);
            FileCopyUtils.copy(fileData, uploadFile);

            log.info("url : " + filePath);

            Camp cpdi = new Camp();
            cpdi.setCpdtNo(cpdtNo);
            cpdi.setCpdiUrl(filePath);
            cpdi.setCampNo(campNo);

            campMapper.cpdiinsert(cpdi);
        }

        return result;
        
    }

    
    @Override
    public List<Camp> campproductUser(Integer userNo) throws Exception {
        List<Camp> productUser = campMapper.campproductUser(userNo);

        for (Camp camp : productUser) {
            camp.setDetailsList(campMapper.campdetailUser(camp.getCampNo()));
        }
        
        return productUser;
    }

    @Override
    public int cpdiinsert(Camp camp) throws Exception {
        int result = campMapper.cpdiinsert(camp);
        return result;
    }

    @Override
    public int detailupdate(Camp camp) throws Exception {  
        int result = campMapper.detailupdate(camp);
        int cpdtNo = camp.getCpdtNo();
        int campNo = camp.getCampNo();

        List<MultipartFile> fileList = camp.getCpdiFiles();

        if(!fileList.isEmpty())
        for(MultipartFile file : fileList){
            if(file.isEmpty()) continue;
            String originName = file.getOriginalFilename();
            long fileSize = file.getSize();
            byte[] fileData = file.getBytes();

            String fileName = UUID.randomUUID().toString() + "_" + originName;
            String filePath = uploadPath + "/" + fileName;

            File uploadFile = new File(uploadPath, fileName);
            FileCopyUtils.copy(fileData, uploadFile);

            log.info("url : " + filePath);

            Camp cpdi = new Camp();
            cpdi.setCpdtNo(cpdtNo);
            cpdi.setCpdiUrl(filePath);
            cpdi.setCampNo(campNo);

            campMapper.cpdiinsert(cpdi);
        }
        return result;
    }

    @Override
    public int detaildelete(int cpdtNo) throws Exception {
        int result = campMapper.detaildelete(cpdtNo);
        return result;
    }

    @Override
    public int cpdidelete(int cpdtNo) throws Exception {
        int result = campMapper.cpdidelete(cpdtNo);
        return result;
    }

    public int campInsert(@RequestBody Camp camp, @RequestParam List<String> facilityTypeNo) throws Exception {
        // String parentTable = "board";
        // log.info("camp : " + camp);
        MultipartFile campLayout = camp.getLayoutFile();
        int environNo = camp.getEnvironmentTypeNo();
        // log.info("environNo : " + environNo);
        // log.info("campLayout : " + campLayout);
        // String newPath = "/img/camp";
        String layoutOriginName = campLayout.getOriginalFilename();
        String layoutFileName = UUID.randomUUID().toString() + "_" + layoutOriginName;
        String layoutFilePath = uploadPath + "/" + layoutFileName;
        byte[] layoutFileData = campLayout.getBytes();
        
        File layoutUploadFile = new File(uploadPath, layoutFileName);
        FileCopyUtils.copy(layoutFileData, layoutUploadFile);           // 파일 업로드 기능 구현

        camp.setCampLayout(layoutFilePath);

        int result = campMapper.campInsert(camp);
        int parentNo = campMapper.maxcampNo();
        // log.info("parentNo : " + parentNo);


        // log.info("오리진 네임 : " + layoutOriginName);
        // 파일 업로드
        List<MultipartFile> fileList = camp.getFile();

        if( !fileList.isEmpty() ) {
            for (MultipartFile file : fileList) {

                if( file.isEmpty() ) continue;

                // 파일 정보 : 원본 파일명, 파일 용량, 파일데이터
                String originName = file.getOriginalFilename();
                byte[] fileData = file.getBytes();

                String fileName = UUID.randomUUID().toString() + "_" + originName;

                // c:/upload/UID_강아지  .png
                String filePath = uploadPath + "/" + fileName; // 이곳에 "/" 안쓰려면 프로퍼티 업로드패스 마지막에 "/" 넣어주면 됨

                // 파일업로드
                // - 서버 측, 파일 시스템에 파일 복사
                // - DB에 파일 정보 등록
                File uploadFile = new File(uploadPath, fileName);
                FileCopyUtils.copy(fileData, uploadFile);           // 파일 업로드 기능 구현

                Camp campList = new Camp();
                campList.setCampNo(parentNo);
                campList.setCpiUrl(filePath);
                campMapper.campImgInsert(campList);
            }
                camp.setCampNo(parentNo);
                for (String s : facilityTypeNo) {
                    // log.info("s : " + s);
                    camp.setFacilitytypeNo(Integer.parseInt(s));
                    campMapper.campFacilityInsert(camp);
                    // int facilNo = camp.getFacilitytypeNo();
                }
                camp.setEnvironmentTypeNo(environNo);
                campMapper.campEnvironmentInsert(camp);
        }
        
        return result;
    }

    /**
     * 예약 진행
     */
    @Override
    public int reservateInsert(Camp camp) throws Exception {

        int result = campMapper.reservateInsert(camp);
        
        return result;
    }

    @Override
    public List<Camp> campSearch(Camp camp) throws Exception {
        List<Camp> searchCamps = campMapper.campSearch(camp);

        return searchCamps;
    }

    @Override
    public int reservationdelete(int reservationNo) throws Exception {
        int result = campMapper.reservationdelete(reservationNo);
        return result;
    }

    @Override
    public List<Camp> campproductadmin() throws Exception {
        List<Camp> productadmin = campMapper.campproductadmin();

        for (Camp camp : productadmin) {
            camp.setDetailsList(campMapper.campdetailUser(camp.getCampNo()));
        }
        
        return productadmin;
    }

    @Override
    public int cpdelete(int campNo) throws Exception {
        int result = campMapper.cpdelete(campNo);
        return result;
    }

    @Override
    public int cpdeletecpi(int campNo) throws Exception {
        int result = campMapper.cpdeletecpi(campNo);
        return result;
    }

    @Override
    public int cpdeletecpdt(int campNo) throws Exception {
        int result = campMapper.cpdeletecpdt(campNo);
        return result;
    }

    @Override
    public int cpdeletecdi(int campNo) throws Exception {
        int result = campMapper.cpdeletecdi(campNo);
        return result;
    }

    @Override
    public List<Camp> reservationNow(Integer userNo) throws Exception {
         List<Camp> reservationList = campMapper.reservationNow(userNo);
        return reservationList;
    }

    @Override
    public int campUpdate(@RequestBody Camp camp, @RequestParam List<String> facilityTypeNo) throws Exception {
        // String parentTable = "board";
        // log.info("camp : " + camp);
        MultipartFile campLayout = camp.getLayoutFile();
        int environNo = camp.getEnvironmentTypeNo();
        // log.info("environNo : " + environNo);
        // log.info("campLayout : " + campLayout);
        // String newPath = "/img/camp";
        String layoutOriginName = campLayout.getOriginalFilename();
        String layoutFileName = UUID.randomUUID().toString() + "_" + layoutOriginName;
        String layoutFilePath = uploadPath + "/" + layoutFileName;
        byte[] layoutFileData = campLayout.getBytes();
        
        File layoutUploadFile = new File(uploadPath, layoutFileName);
        FileCopyUtils.copy(layoutFileData, layoutUploadFile);           // 파일 업로드 기능 구현

        camp.setCampLayout(layoutFilePath);

        int result = campMapper.campUpdate(camp);
        int parentNo = camp.getCampNo();
        // log.info("parentNo : " + parentNo);


        // log.info("오리진 네임 : " + layoutOriginName);
        // 파일 업로드
        List<MultipartFile> fileList = camp.getFile();

        if( !fileList.isEmpty() ) {
            for (MultipartFile file : fileList) {

                if( file.isEmpty() ) continue;

                // 파일 정보 : 원본 파일명, 파일 용량, 파일데이터
                String originName = file.getOriginalFilename();
                byte[] fileData = file.getBytes();

                String fileName = UUID.randomUUID().toString() + "_" + originName;

                // c:/upload/UID_강아지  .png
                String filePath = uploadPath + "/" + fileName; // 이곳에 "/" 안쓰려면 프로퍼티 업로드패스 마지막에 "/" 넣어주면 됨

                // 파일업로드
                // - 서버 측, 파일 시스템에 파일 복사
                // - DB에 파일 정보 등록
                File uploadFile = new File(uploadPath, fileName);
                FileCopyUtils.copy(fileData, uploadFile);           // 파일 업로드 기능 구현

                Camp campList = new Camp();
                campList.setCampNo(parentNo);
                campList.setCpiUrl(filePath);
                campMapper.campImgInsert(campList);
            }
                camp.setCampNo(parentNo);
                for (String s : facilityTypeNo) {
                    // log.info("s : " + s);
                    camp.setFacilitytypeNo(Integer.parseInt(s));
                    campMapper.campFacilityInsert(camp);
                    // int facilNo = camp.getFacilitytypeNo();
                }
                camp.setEnvironmentTypeNo(environNo);
                campMapper.campEnvironmentInsert(camp);
        }
        
        return result;
    }

    @Override
    public int campFacilityDelete(int campNo) throws Exception {
        int result = campMapper.campFacilityDelete(campNo);
        return result;
    }

    @Override
    public int campImgDelete(int campNo) throws Exception {
        int result = campMapper.campImgDelete(campNo);
        return result;
    }

    @Override
    public int campEnvironmentDelete(int campNo) throws Exception {
        int result = campMapper.campEnvironmentDelete(campNo);
        return result;
    }

    
}
