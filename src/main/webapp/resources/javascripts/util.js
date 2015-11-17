/**
 * 扩展原有的Date 对象的format 方法 eg:format="YYYY-MM-dd hh:mm:ss";
 */
Date.prototype.format = function(format){

    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    };

    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    };

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};


/** 日期工具类 */
var DateUtil = {};

/**
 * 获取与当前时间的时间差
 */
DateUtil.compareTime = function(preTime){

	if (preTime === 0){
		return "";
	}

    var curTimeTick = new Date().getTime();
    var preTimeStr = preTime + "";

    var interval = 0;

    if (preTimeStr.indexOf("-") < 0) {
        interval = parseInt((curTimeTick - preTime) / 1000);
    } else if(preTimeStr.indexOf("-") > 0) {
        var arrDate = preTimeStr.substring(0, 10).split("-");
        preDate = arrDate[1] + "/" + arrDate[2] + "/" + arrDate[0] + " " + preTimeStr.substring(10, 19);
        interval = parseInt((curTimeTick - Date.parse(preDate)) / 1000);
    }

    // 根据时间间隔的秒数，获取描述文字
    if (interval >= 0 && interval < 60) {
        return interval + "秒前";
    }else if (interval >= 60 && interval < 3600) {
        var minute = parseInt(interval / 60);
        return minute + "分钟前";
    }else if (interval >= 3600 && interval < 3600 * 24) {
        var hour = parseInt(interval / 3600);
        return hour + "小时前";
    } else {
        // dateVal 表示指定日期与 1970 年 1 月 1 日午夜间全球标准时间 的毫秒数。
        if (preTimeStr.indexOf("-") < 0) {
            var dateVal = preTime;
            var date = new Date(dateVal);
            return date.format("yyyy-MM-dd hh:mm:ss");
        }else {
            return preTime;
        }
    }
};