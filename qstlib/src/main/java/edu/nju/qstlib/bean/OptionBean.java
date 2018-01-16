package edu.nju.qstlib.bean;

/**
 * 选项Bean
 */
public class OptionBean {
    private int optionId;
    private String optionContent;
    private boolean isRight;


    public OptionBean() {
    }

    /**
     * 选项Bean
     * @param optionId 选项ID
     * @param optionContent 选项内容
     * @param isRight 是否正确
     */
    public OptionBean(int optionId, String optionContent, boolean isRight) {
        this.optionId = optionId;
        this.optionContent = optionContent;
        this.isRight = isRight;
    }

    public int getOptionId() {
        return optionId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    public void setRight(boolean right) {
        isRight = right;
    }
}
