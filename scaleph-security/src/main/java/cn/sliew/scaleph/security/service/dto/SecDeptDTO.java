package cn.sliew.scaleph.security.service.dto;

import javax.validation.constraints.NotNull;

import cn.sliew.scaleph.common.dto.BaseDTO;
import cn.sliew.scaleph.system.service.vo.DictVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * @author gleiyu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "部门信息", description = "部门表")
public class SecDeptDTO extends BaseDTO {
    private static final long serialVersionUID = 1457138850402052741L;

    @ApiModelProperty(value = "部门编号")
    private String deptCode;

    @NotNull
    @Length(max = 60)
    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "上级部门id")
    private Long pid;

    @ApiModelProperty(value = "部门状态")
    private DictVO deptStatus;

}