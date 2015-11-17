package sample;

import java.util.List;

/**
 * 包贴打印信息
 *
 * @author wei_jc
 * @since 1.0
 */
public class PackInfo {
    private int packIdx; // 第几包
    private String packCode; // 包号
    private String clientName; // 客户名称
    private String clientAddr; // 客户地址
    private String clientPhone; // 客户电话
    private String contactMan; // 联系人
    private int totalCount; // 品种数
    private int totalAmount; // 册数
    private String sellReason; // 订单依据
    private String sendDepartment; // 发货单位
    private String sendTel; // 发货电话
    private String sendContact; // 发货联系人
    List<Product> products;

    public int getPackIdx() {
        return packIdx;
    }

    public void setPackIdx(int packIdx) {
        this.packIdx = packIdx;
    }

    public String getPackCode() {
        return packCode;
    }

    public void setPackCode(String packCode) {
        this.packCode = packCode;
    }

    public String getClientName() {
        if(clientName != null && clientName.getBytes().length > 48) {
            return UtilString.substringByByte(clientName, 0, 48) + "...";
        }
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientAddr() {
        return clientAddr;
    }

    public void setClientAddr(String clientAddr) {
        this.clientAddr = clientAddr;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getContactMan() {
        return contactMan;
    }

    public void setContactMan(String contactMan) {
        this.contactMan = contactMan;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSellReason() {
        if(sellReason != null && sellReason.getBytes().length > 26) {
            return UtilString.substringByByte(sellReason, 0, 26) + "...";
        }
        return sellReason;
    }

    public void setSellReason(String sellReason) {
        this.sellReason = sellReason;
    }

    public String getSendDepartment() {
        return sendDepartment;
    }

    public void setSendDepartment(String sendDepartment) {
        this.sendDepartment = sendDepartment;
    }

    public String getSendTel() {
        return sendTel;
    }

    public void setSendTel(String sendTel) {
        this.sendTel = sendTel;
    }

    public String getSendContact() {
        return sendContact;
    }

    public void setSendContact(String sendContact) {
        this.sendContact = sendContact;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
