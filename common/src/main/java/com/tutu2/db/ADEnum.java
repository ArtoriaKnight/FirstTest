package com.tutu2.db;

public class ADEnum {

    public enum PopUpType {
        INDEX(1)/*** 首页弹窗 */,
        VIP(2)/*** VIP弹窗 */;

        private int code;

        PopUpType(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum Rule {
        NEW_USER(1)/*** 新用户 */,
        NOT_JOIN_EXPERIENCE(2)/*** 未参加体验课 */;

        private int code;

        Rule(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum ADForUser {
        NO_NEED(0.0)/*** 不匹配规则（不需要弹窗） */,
        NEED(1.0)/*** 需要（还未弹窗） */,
        ALREADY(2.0)/*** 已经弹窗（针对仅一次那种） */;

        private double code;

        ADForUser(double code) {
            this.code = code;
        }

        public double getCode() {
            return code;
        }
    }

}
