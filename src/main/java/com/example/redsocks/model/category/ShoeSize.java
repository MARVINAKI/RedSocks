package com.example.redsocks.model.category;

public enum ShoeSize {
    RU16(16), RU16_5(16.5), RU17(17), RU18(18), RU19(19), RU19_5(19.5), RU20(20), RU21(21),
    RU22(22), RU22_5(22.5), RU23(23), RU24(24), RU25(25), RU25_5(25.5), RU26(26), RU27(27),
    RU27_5(27.5), RU28(28), RU28_5(28.5), RU29(29), RU29_5(29.5), RU30(30), RU31(31), RU31_5(31.5),
    RU32(32), RU33(33), RU34(34), RU34_5(34.5), RU35(35), RU36(36), RU36_5(36.5), RU37(37),
    RU37_5(37.5), RU38(38), RU38_5(38.5), RU39(39), RU40(40), RU40_5(40.5), RU41(41), RU42(42),
    RU43(43), RU43_5(43.5), RU44(44), RU45(45), RU45_5(45.5), RU46(46), RU46_5(46.5), RU47(47),
    RU47_5(47.5), RU48(48);
    private final double number;

    ShoeSize(double number) {
        this.number = number;
    }

    public final double getNumber() {
        return number;
    }
}
