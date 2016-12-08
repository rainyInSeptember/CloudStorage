$(document).ready(function () {
    tableInit();

    //页面加载、点击切换table的时候调用
    function tableInit() {
        //设置当前路径
        $("#currentPath").text("/");
        $(".goback").hide();

        //修改表格头

        $("#frame-head-tr").html("<div class='frame-head-filename'>文件名</div>" +
            "<div class='frame-head-filesize'>文件大小</div>" +
            "<div class='frame-head-modifytime'>修改时间</div>");

        var goToPath = "/";
        $.ajax({
            data: "goToPath=" + goToPath,

            url: "browseDictionary/browseDictionary",
            success: function (data1) {

                //加载最外层table
                var data = eval("(" + data1 + ")");
                var datalength = data.length;
                $(".table-content-div").html("<table class='table-content'></table>");
                $(".filenum").html("共加载：" + data[datalength - 1].filenum + "个&nbsp;&nbsp;&nbsp;&nbsp;");
                //添加tr最外层文件信息
                for (var i = 0; i < datalength - 1; i++) {

                    $(".table-content").append(
                        "<tr class='root' path='/" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "' >" +
                        "<td class='table-checkbox'><input type='checkbox' filesize=" + data[i].filesize + " filetype=" + data[i].filetype + " name='singleCheckbox' path='/" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "' class='content-checkbox' onclick='countCheck()'/></td>" +
                        "<td class='filename' filetype=" + data[i].filetype + "><img src='img/filetype/" + data[i].filetype + ".png'/><a class='tc-a-filename'>" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "</a><img class='img-ok' src='img/ok.png'><img class='img-remove' src='img/remove.png'></td>" +
                        "<td class='filesize'>" + data[i].filesize + "</td>" +
                        "<td class='modifytime'>" + decodeURIComponent(decodeURIComponent(data[i].modefitime)) + "</td>" +
                        "</tr>");
                }
            },
            error: function () {
                //调用消息提示模态框！！！！1
                alert("出错啦，请重试......");
            },
        });
    }


    //table点击处理
    $(".list-2").bind("click", function () {
        $(".list-1").css("backgroundPosition", " 0px 0px");
        $(".list-2").css("backgroundPosition", "-30px 0px");
        tableInit();//初始化
    });


    //点击切换到div时候调用
    function divInit() {
        //设置当前路径
        $("#currentPath").text("/");
        $(".goback").hide();
        var goToPath = "/";
        $.ajax({
            data: "goToPath=" + goToPath,
            url: "browseDictionary/browseDictionary",
            success: function (data1) {
                //移除table
                $(".table-content").remove();
                //修改头部"
                $("#frame-head-tr").html("");

                //多次点击，内容在初始化！！！！
                $(".table-content-div").html("");
                var data = eval("(" + data1 + ")");
                var datalength = data.length;
                $(".filenum").html("共加载：" + data[datalength - 1].filenum + "个&nbsp;&nbsp;&nbsp;&nbsp;");
                //添加div最外层文件信息
                for (var i = 0; i < datalength - 1; i++) {
                    $(".table-content-div").append("<div class='div-content' filetype=" + data[i].filetype + " path=" + '/' + decodeURIComponent(decodeURIComponent(data[i].filename)) + " ><div class='div-content-image' >" +
                        "<input filetype=" + data[i].filetype + " filesize=" + data[i].filesize + " path=" + '/' + decodeURIComponent(decodeURIComponent(data[i].filename)) + " type='checkbox' name='singleCheckbox' onclick='countCheck()' class='content-checkbox'/ >" +
                        "<img class='div-file-img' src='img/filetype/div" + data[i].filetype + ".png'/>" +
                        "</div><div class='div-content-filename'><a  class='dc-a-filename'>" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "</a>" +
                        "<img class='img-ok' src='img/ok.png'>" +
                        "<img class='img-remove' src='img/remove.png'>" +
                        "</div>" +
                        "</div>");
                }
            },
            error: function () {
                alert("出错啦，请重试......");
            },
        });
    }

    //div点击处理
    $(".list-1").bind("click", function () {
        $(".list-1").css("backgroundPosition", " 0px -26px");
        $(".list-2").css("backgroundPosition", "-30px -26px");
        divInit();
    });

    //为文件夹div添加点击事件
    $(".table-content-div").on("click", "a[class='dc-a-filename']", function (e) {
        var goToPath;
        var currentDiv = null;
        //处理异常当前点击的是，当前点击的是div
        if ($(e.target).attr("class") == "div-content") {
            currentDiv = $(e.target);
        }
        else if (($(e.target).attr("class") == "div-content-image") || $(e.target).attr("class") == "div-content-filename") {
            currentDiv = $(e.target).parent();
        }
        else {
            currentDiv = $(e.target).parent().parent();
        }

        goToPath = $(currentDiv).attr("path");
        if ($(currentDiv).attr("filetype") == "dictionary") {
            $("#currentPath").text(goToPath);
            //添加返回上一级
            $(".goback").show();

            //查询
            gotoDiv(goToPath);
        }
        else {
            return false;
        }


    });

    function gotoDiv(goToPath) {

        $.ajax({
            data: "goToPath=" + encodeURIComponent(encodeURIComponent(goToPath)),
            url: "browseDictionary/browseDictionary",
            success: function (data1) {
                //移除table
                $(".table-content").remove();
                $(".table-content-div").html("");
                var data = eval("(" + data1 + ")");
                var datalength = data.length;
                alert(datalength);
                // $(".filenum").html("共加载："+data[datalength-1].filenum+"个&nbsp;&nbsp;&nbsp;&nbsp;");
                //添加div最外层文件信息
                for (var i = 0; i < datalength; i++) {
                    $(".table-content-div").append("<div class='div-content' filetype=" + data[i].filetype + " path=" + goToPath + "/" + decodeURIComponent(decodeURIComponent(data[i].filename)) + ">" +
                        "<div class='div-content-image' >" +
                        "<input type='checkbox' filesize=" + data[i].filesize + " filetype=" + data[i].filetype + " path=" + goToPath + "/" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "  name='singleCheckbox' onclick='countCheck()' class='content-checkbox'/ >" +
                        "<img class='div-file-img' src='img/filetype/div" + data[i].filetype + ".png'/> " +
                        "</div><div class='div-content-filename'>" +
                        "<a class='dc-a-filename'>" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "</a>" +
                        "<img class='img-ok' src='img/ok.png'>" +
                        "<img class='img-remove' src='img/remove.png'>" +
                        "</div>" +
                        "</div>");
                }
            },
            error: function () {
                alert("出错啦，请重试......");
            },
        });
    }

    //为table 文件夹添加点击事件
    $(".table-content-div").on("click", "a[class='tc-a-filename']", function (e) {


        var currentTr;
        currentTr = $(e.target).parent().parent();
        var goToPath = $(currentTr).attr("path");

        if ($(e.target).parent().attr("filetype") == 'dictionary') {
            //添加返回上一级功能
            $(".goback").show();
            //为文件夹则查询
            $("#currentPath").text(decodeURIComponent(decodeURIComponent(goToPath)));
            gotoTable(goToPath);

        }
        else {
            //为文件则false
            return false;
        }


    });

    function gotoTable(goToPath) {
        $.ajax({
            data: "goToPath=" + encodeURIComponent(encodeURIComponent(goToPath)),
            url: "browseDictionary/browseDictionary",
            success: function (data1) {
                //加载最外层table
                var data = eval("(" + data1 + ")");
                var datalength = data.length;

                $(".table-content-div").html("<table class='table-content'></table>");
                $(".filenum").html("共加载：" + data[datalength - 1].filenum + "个&nbsp;&nbsp;&nbsp;&nbsp;");
                //添加tr最外层文件信息
                for (var i = 0; i < datalength - 1; i++) {

                    $(".table-content").append("<tr path=" + goToPath + "/" + decodeURIComponent(decodeURIComponent(data[i].filename)) + " filetype=" + data[i].filetype + "><td class='table-checkbox'>" +
                        "<input type='checkbox' filesize=" + data[i].filesize + " filetype=" + data[i].filetype + " filesize=" + data[i].filesize + "  class='allcheckbox' name='singleCheckbox' path=" + goToPath + "/" + decodeURIComponent(decodeURIComponent(data[i].filename)) + " onclick='countCheck()'/></td>" +
                        "<td class='filename' filetype=" + data[i].filetype + "><img src='img/filetype/" + data[i].filetype + ".png'/><a class='tc-a-filename'>" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "</a><img class='img-ok' src='img/ok.png'><img class='img-remove' src='img/remove.png'></td>" +
                        "<td class='filesize'>" + data[i].filesize + "</td>" +
                        "<td class='modifytime'>" + decodeURIComponent(decodeURIComponent(data[i].modifytime)) + "</td></tr>");
                }

            },
            error: function () {
                alert("出错啦，请重试......");
            },
        });
    }

    $(".goback").click(function () {

        var path = $("#currentPath").text();
        var index = path.lastIndexOf("/");
        if (index == 0) {
            goToPath = "/";
        }
        else {
            goToPath = path.substring(0, index);
        }
        $("#currentPath").text(goToPath);

        if ($(".table-content-div").has("table").length) {
            //调用tab
            if (goToPath == "/") {
                tableInit();
            }
            else {
                gotoTable(goToPath);
            }

        }
        else {
            //调用div
            if (goToPath == "/") {
                divInit();
            }
            else {
                gotoDiv(encodeURIComponent(encodeURIComponent(goToPath)));
            }


        }

    });
});




