package me.fulln.base.model.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @AUthor: fulln
 * @Description: 实体类
 * @Date : Created in  15:13  2019/1/19.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LogOperationInfo implements Serializable {

    private static final long serialVersionUID = -4119371694611877184L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long operationId;
    private String operationUser;
    private Long operationDate;
    private String operationUrl;
    private String userIp;
    private String operationParams;
    private String requestType;
    private String requestHead;
    private String operationMethod;

}
