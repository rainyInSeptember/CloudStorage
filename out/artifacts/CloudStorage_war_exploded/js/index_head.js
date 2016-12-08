function getSelectedCheckBoxNum() {
    var obj = document.getElementsByName("singleCheckbox");
    var count = 0;

    for (var i = 0; i < obj.length; i++) {
        if (obj[i].checked) {
            count++;
        }
    }
    return count;
}
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
            alert(data1);
            var data = eval("(" + data1 + ")");
            //加载最外层table
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
                    "<td class='modifytime'>" + decodeURIComponent(decodeURIComponent(data[i].modifytime)) + "</td>" +
                    "</tr>");
            }
        },
        error: function () {
            alert("出错啦，请重试......");
        },
    });
}
//点击切换到div时候调用
function divInit() {
    //设置当前路径
    $("#currentPath").text("/");
    $(".goback").hide();
    var goToPath = "/";
    $.ajax({
        data: "goToPath=" + goToPath,
        url: "browseDictionary/browseDictionary",
        success: function (data) {
            //移除table
            $(".table-content").remove();
            //修改头部"
            $("#frame-head-tr").html("");

            //多次点击，内容在初始化！！！！
            $(".table-content-div").html("");

            var datalength = data.length;
            $(".filenum").html("共加载：" + data[datalength - 1].filenum + "个&nbsp;&nbsp;&nbsp;&nbsp;");
            //添加div最外层文件信息
            for (var i = 0; i < datalength - 1; i++) {
                $(".table-content-div").append("<div class='div-content' filesize=" + data[i].filesize + " filetype=" + data[i].filetype + " path=" + '/' + decodeURIComponent(decodeURIComponent(data[i].filename)) + " ><div class='div-content-image' >" +
                    "<input filetype=" + data[i].filetype + " path=" + '/' + decodeURIComponent(decodeURIComponent(data[i].filename)) + " type='checkbox' filesize=" + data[i].filesize + "  name='singleCheckbox' onclick='countCheck()' class='content-checkbox'/ >" +
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
function countCheck() {

    var count = getSelectedCheckBoxNum();
    //勾选多于一个文件 按钮不可用


    //没有勾选的复选框
    if (count == 0) {
        document.getElementById("frame-head-tr").innerHTML = "<div class='frame-head-filename'>文件名</div>" +
            "<div class='frame-head-filesize'>文件大小</div>" +
            "<div class='frame-head-modifytime'>修改时间</div>";
    }
    else {
        document.getElementById("frame-head-tr").innerHTML = "已选中" + count + "个文件/文件夹<button type='button' class='btn btn-primary' id='share-btn' onclick='fileshare()'>分享</button>" +
            "<button type='button' class='btn btn-primary' id='download-btn' onclick='filedownload()'>下载</button>" +
            "<button type='button'  class='btn btn-primary' id='delete-btn' onclick='getDeletePath()'>删除</button>" +
            "<button type='button' class='btn btn-primary' id='rename-btn' onclick='filerename()')>重命名</button>" +
            "<button type='button' class='btn btn-primary' id='copy-btn' onclick='filecopy()'>复制到</button>" +
            "<button type='button' class='btn btn-primary' id='move-btn' onclick='filemove()'>移动到</button>";
    }
    if (count > 1) {
        $("#rename-btn").attr("disabled", "disabled");
        $("#download-btn").attr("disabled", "disabled");
        $("#share-btn").attr("disabled", "disabled");
    }
    return false;
}


/**
 * 获取要删除的文件路径
 * 返回文件路径数组
 */
function getDeletePath() {
    var count = getSelectedCheckBoxNum();
    var deleteFilePath = new Array();
    var selectedCheckboxs = document.getElementsByName("singleCheckbox");
    var j = 0;
    for (var i = 0; i < selectedCheckboxs.length; i++) {
        if (selectedCheckboxs[i].checked) {
            deleteFilePath[j] = encodeURIComponent(encodeURIComponent(selectedCheckboxs[i].getAttribute("path")));
            j++;
        }
    }
    $("#prompts-sure-btn").show();
    $('#prompts-modal-content').html("<h4>删除" + decodeURIComponent(decodeURIComponent(deleteFilePath[0])) + "等," + count + "个文件/文件夹，删除后无法恢复！</h4>");
    $('#prompts-modal').modal('show');

}

/**
 * 点击确定后，删除文件、文件夹
 */

function deleteFile() {
    //要删除的文件
    var deletePath = getSelectedPath();
//	location.href="DeleteController/Delete?deletePath="+deletePath;

    $.ajax({
        data: "deletePath=" + deletePath,
        url: "DeleteController/Delete",
        success: function (data) {
            alert("删除成功");
            refreshPage();
        },
        error: function () {
            alert("出现错误");
        },
    });
    //隐藏提示框
    $('#prompts-modal').modal('hide');
}

/**    文件下载
 * 只能为文件不支持文件夹下载
 *
 */
function filedownload() {
    var downloadFilePath = new Array();
    var filesize = new Array();
    var selectedCheckboxs = document.getElementsByName("singleCheckbox");
    var j = 0;
    for (var i = 0; i < selectedCheckboxs.length; i++) {
        if (selectedCheckboxs[i].checked) {
            if (selectedCheckboxs[i].getAttribute("filetype") == "dictionary") {
//				$('#prompts-modal-content').html("<h4>暂不支持文件夹下载。。。。。</h4>");
//				$("#prompts-sure-btn").hide();
//				$('#prompts-modal').modal('show');
                $("#download-btn").attr("disabled", "disabled");
            }
            else {


                downloadFilePath[j] = encodeURIComponent(encodeURIComponent(selectedCheckboxs[i].getAttribute("path")));
                filesize[j] = selectedCheckboxs[i].getAttribute("filesize");
                j++;
            }
        }

    }
    for (var i = 0; i < filesize.length; i++) {

        window.open("fileDownload/fileDownload/?downloadFilePath=" + downloadFilePath[i] + "&filesize=" + filesize[i], "_self");
    }

}


/**
 *
 * 文件重命名
 */


function filerename() {
    var selectedCheckboxs = document.getElementsByName("singleCheckbox");
    var j = 0;
    var currentCheckBox = null;
    var currentFileNameTdDiv = null;
    var fileName;
    var changedFilename = null;
    var filePath = null;

    for (var i = 0; i < selectedCheckboxs.length; i++) {
        if (selectedCheckboxs[i].checked) {
            currentCheckBox = selectedCheckboxs[i];
            $(currentCheckBox).attr("disabled", "true");
            //获取承载文件名的td
            if ($(".table-content-div").has("table").length) {
                //table   获取td
                currentFileNameTdDiv = $(currentCheckBox).parent().next();
            }
            else {
                //div    获取div
                currentFileNameTdDiv = $(currentCheckBox).parent().next();
            }
            j++;

        }

    }
    //获取文件名
    fileName = $(currentFileNameTdDiv).children("a").text();

    var input = $("<input class='renameInput' type='text'value='" + fileName + "'/>");
    var aTd = $("<a  class='tc-a-filename'>" + fileName + "</a>");
    var aDiv = $("<a  class='dc-a-filename'>" + fileName + "</a>");

    $(currentFileNameTdDiv).children("a").replaceWith(input);
    $(currentFileNameTdDiv).children("img[class='img-ok']").show();
    $(currentFileNameTdDiv).children("img[class='img-remove']").show();

    //为图片点击点击事件  ok remove
    // 文本输入变化，事件获取输入文本值
    $(currentFileNameTdDiv).children("input[class='renameInput']").change(function () {
        changedFilename = $(currentFileNameTdDiv).children("input[class='renameInput']").val();
    });


    $(currentFileNameTdDiv).children("img[class='img-ok']").on("click", function () {
        $(currentFileNameTdDiv).children("input[class='renameInput']").trigger("change");

        filePath = $(currentFileNameTdDiv).parent().attr("path");

        if (changedFilename == fileName) {//文件名不变
            $(currentFileNameTdDiv).children("img[class='img-remove']").trigger("click");
        }
        else {
            //修改文件名 获得文件全路径

            filePath = encodeURIComponent(encodeURIComponent(filePath));
            fileName = encodeURIComponent(encodeURIComponent(fileName));
            $.ajax({
                data: "filePath=" + filePath + "&fileName=" + fileName + "&changedFilename=" + encodeURIComponent(encodeURIComponent(changedFilename)),
                url: "FileRenameController/fileRename",
                success: function (data) {
                    refreshPage();

                },
                error: function () {
                    alert("出错啦，请重试......");
                },
            });


        }
        $(currentFileNameTdDiv).children("img[class='img-ok']").off("click");
    });


    $(currentFileNameTdDiv).children("img[class='img-remove']").on("click", function () {
        //换回a标签

        if ($(".table-content-div").has("table").length) {
            $(currentFileNameTdDiv).children("input").replaceWith(aTd);
        }
        else {
            $(currentFileNameTdDiv).children("input").replaceWith(aDiv);
        }
        $(currentCheckBox).removeAttr("disabled");
        $(currentFileNameTdDiv).children("img[class='img-ok']").hide();
        $(currentFileNameTdDiv).children("img[class='img-remove']").hide();
        //解绑
        $(currentFileNameTdDiv).children("img[class='img-remove']").off("click");
    });


}

function refreshPage() {
    var currentPath = $("#currentPath").text();
    if ($(".table-content-div").has("table").length) {
        if (currentPath == "/") {
            tableInit();//不能调用
        }
        else {
            gotoTable(encodeURIComponent(encodeURIComponent(currentPath)));
        }
    }

    else {
        if (currentPath == "/") {
            divInit();
        }
        else {

            gotoDiv(encodeURIComponent(encodeURIComponent(currentPath)));
        }

    }
}


function gotoTable(goToPath) {

    $.ajax({
        data: "goToPath=" + goToPath,
        url: "browseDictionary/browseDictionary",
        success: function (data) {
            //加载最外层table
            var datalength = data.length;
            $(".table-content-div").html("<table class='table-content'></table>");
            $(".filenum").html("共加载：" + data[datalength - 1].filenum + "个&nbsp;&nbsp;&nbsp;&nbsp;");
            //添加tr最外层文件信息
            for (var i = 0; i < datalength - 1; i++) {

                $(".table-content").append("<tr path=" + goToPath + "/" + data[i].filename + " filetype=" + data[i].filetype + "><td class='table-checkbox'>" +
                    "<input type='checkbox' filetype=" + data[i].filetype + " filesize=" + data[i].filesize + "  class='allcheckbox' name='singleCheckbox' path=" + goToPath + "/" + data[i].filename + " onclick='countCheck()'/></td>" +
                    "<td class='filename' filetype=" + data[i].filetype + "><img src='img/filetype/" + data[i].filetype + ".png'/><a class='tc-a-filename'>" + data[i].filename + "</a><img class='img-ok' src='img/ok.png'><img class='img-remove' src='img/remove.png'></td>" +
                    "<td class='filesize'>" + data[i].filesize + "</td>" +
                    "<td class='modifytime'>" + data[i].modifytime + "</td></tr>");
            }

        },
        error: function () {
            alert("出错啦，请重试......");
        },
    });
}
function gotoDiv(goToPath) {

    $.ajax({
        data: "goToPath=" + goToPath,
        url: "browseDictionary/browseDictionary",
        success: function (data1) {
            alert(data1);
            //移除table
            $(".table-content").remove();
            $(".table-content-div").html("");
            var data = eval("(" + data1 + ")");
            var datalength = data.length;
            alert(datalength);
            $(".filenum").html("共加载：" + data[datalength - 1].filenum + "个&nbsp;&nbsp;&nbsp;&nbsp;");
            //添加div最外层文件信息
            for (var i = 0; i < datalength; i++) {
                $(".table-content-div").append("<div class='div-content' filetype=" + data[i].filetype + " path=" + goToPath + "/" + decodeURIComponent(decodeURIComponent(data[i].filename)) + ">" +
                    "<div class='div-content-image' >" +
                    "<input type='checkbox' filetype=" + data[i].filetype + " path=" + goToPath + "/" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "  name='singleCheckbox' onclick='countCheck()' class='content-checkbox'/ >" +
                    "<img class='div-file-img' src='img/filetype/div" + data[i].filetype + ".png'/> " +
                    "</div><div class='div-content-filename'>" +
                    "<a class='dc-a-filename'>" + decodeURIComponent(decodeURIComponent(data[i].filename)) + "</a" +
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

/**
 * 文件复制
 */
function filecopy() {
    showDicSelectedModal();
    var copyFilePath;
    var copyToPath;
    //取消绑定事件
    $("#copyAndMoveBtn").unbind();
    //重新绑定事件
    $("#copyAndMoveBtn").on("click", function () {
        //获取复制路径 获取复制元素  调用方法
        //获取勾选的文件额路径
        copyFilePath = getSelectedPath();
        copyToPath = $("#current-file-path").text();

        alert(copyFilePath+"d");
        alert(copyToPath+"to")
        for (var i = 0; i < copyFilePath.length; i++) {

            if (copyFilePath[i] == copyFilePath) {
                alert("不能将文件复制到自身");
                return false;
            }

        }
        $.ajax({

            data: "copyFilePath=" + copyFilePath + "&copyToPath=" + encodeURIComponent(encodeURIComponent(copyToPath)),
            url: "CopyAndMove/Copy",
            success: function (data) {
                //alert("文件/文件夹复制成功");
                //刷新当前div或者table
                refreshPage();
            },
            error: function () {

            },
        });
        $("#file-info-myModal").modal("hide");

    });

}
function fileshare() {
    $("#share_info").html("");

    var selectedCheckboxs = document.getElementsByName("singleCheckbox");
    for (var i = 0; i < selectedCheckboxs.length; i++) {
        if (selectedCheckboxs[i].checked) {
            if (selectedCheckboxs[i].getAttribute("filetype") == "dictionary") {
                $("#share-btn").attr("disabled", "disabled");
            }
            else {
                $("#file-share-myModal").modal("show");
            }
        }

    }
}

function share_open() {
    var shareFilePath;
    shareFilePath = getSelectedPath();

    //要分享的文件链接
    $.ajax({
        data: "shareFilePath=" + decodeURIComponent(decodeURIComponent(shareFilePath)) + "&type=" + "open",
        url: "fileShare/fileShare",
        success: function (data) {

            $("#share_info").html(decodeURIComponent(data));
        },
        error: function () {
            alert("error");
        },
    });


    //插入数据到数据库  返回链接对应 地址 或者id
}

function share_private() {

    var shareFilePath;
    shareFilePath = getSelectedPath();
    //要分享的文件链接

//插入数据到数据库  返回链接对应 地址 或者id 以及验证密码
    $.ajax({
        data: "shareFilePath=" + shareFilePath + "&type=" + "private",
        url: "fileShare/fileShare",
        success: function (data) {

            $("#share_info").html(decodeURIComponent(data));
        },
        error: function () {
            alert("error");
        },
    });


}

//function share_copy()
//{
//	alert("复制链接");
//
//}

/**
 * 文件移动
 */
function filemove() {
    //获取选择的文件目录
    showDicSelectedModal();
    var moveFilePath;
    var moveToPath;

    //$("#current-file-path").text();
    //取消绑定事件
    $("#copyAndMoveBtn").unbind();
    //重新绑定事件
    $("#copyAndMoveBtn").on("click", function () {
        //获取移动路径 获取移动元素  调用方法
        //获取勾选的文件路径
        moveFilePath = getSelectedPath();
        moveToPath = $("#current-file-path").text();

        alert(moveFilePath+"当前");
        alert(moveToPath+"目标");
        for (var i = 0; i < moveFilePath.length; i++) {

            if (decodeURIComponent(decodeURIComponent(moveFilePath[i])) == moveToPath) {
                alert("不能将文件移动到自身！");
                return false;
            }
        }
        $.ajax({
            data: "moveFilePath=" + moveFilePath + "&moveToPath=" + encodeURIComponent(encodeURIComponent(moveToPath)),
            url: "CopyAndMove/Move",
            success: function (data) {
                refreshPage();
            },
            error: function () {
            },
        });
        $("#file-info-myModal").modal("hide");
    });


}
function showDicSelectedModal() {
    //当前目录为"/"
    $("#current-file-path").text("/");
    var goToPath = "/";
    //清空文件夹显示
    $(".file-info-ul").html("");
    $.ajax({
        data: "goToPath=" + goToPath,
        url: "CopyAndMove/CopyAndMove",
        success: function (data1) {
            var data = eval("(" + data1 + ")");
            for (var i = 0; i < data.length; i++) {
                $(".file-info-ul").append("<li class='root'parent='/'><img src='img/filetype/dictionary.png'>" + decodeURIComponent(decodeURIComponent(data[i])) + "</li>");
            }
        },
        error: function () {
            alert("error");
        },
    });

    $("#file-info-myModal").modal("show");
}

/**
 * 获取勾选的文件路径
 * 返回数组
 */
function getSelectedPath() {
    var filePath = new Array();
    var selectedCheckboxs = document.getElementsByName("singleCheckbox");
    var j = 0;
    for (var i = 0; i < selectedCheckboxs.length; i++) {
        if (selectedCheckboxs[i].checked) {

            filePath[j] = encodeURIComponent(encodeURIComponent(selectedCheckboxs[i].getAttribute("path")));
            j++;

        }

    }
    return filePath;
}

function createDic() {
    //创建文件夹的当前目录
    var currentPath = $("#currentPath").text();
    var currentTrDiv;
    var removeTrDiv;
    var dicname = null;
    var tr = $("<tr id='createDicTr'>" +
        "<td class='table-checkbox'>" +
        "<input type='checkbox' filetype='dictionary'   class='content-checkbox' />" +
        "</td><td class='filename' filetype='dictionary'>" +
        "<img src='img/filetype/dictionary.png' />" +
        "<input id='createDicInput'  type='text' value=' '/>" +
        "<img class='img-ok' src='img/ok.png'>" +
        "<img class='img-remove' src='img/remove.png'>" +
        "</td>" +
        "<td class='filesize'>--</td>" +
        "<td class='modifytime'>--</td>" +
        "</tr>");
    var div = $("<div class='div-content'  id='createDicDiv'><div class='div-content-image' >" +
        "<input  type='checkbox'  class='content-checkbox'/ >" +
        "<img class='div-file-img' src='img/filetype/divdictionary.png'/>" +
        "</div><div class='div-content-filename'> <input id='createDicInput'  type='text' value=' '/>" +
        "<img class='img-ok' src='img/ok.png'>" +
        "<img class='img-remove' src='img/remove.png'>" +
        "</div>" +
        "</div>");
    if ($(".table-content-div").has("table").length) {
        //table中添加一项
        $(".table-content").prepend(tr);
        currentTrDiv = $("#createDicTr");
        removeTrDiv = $("#createDicTr");

    }

    else {
        //div中添加一项
        $(".table-content-div").prepend(div);

        currentTrDiv = $("#createDicDiv");
        removeTrDiv = $("#createDicDiv");

    }

    //获取输入框中的值
    $("#createDicInput").change(function () {
        dicname = $("#createDicInput").val();
    });
    $(currentTrDiv).find("img[class='img-ok']").show();
    $(currentTrDiv).find("img[class='img-remove']").show();

    $(currentTrDiv).find("img[class='img-ok']").on("click", function () {
        if (dicname == null || dicname == "") {
            $(removeTrDiv).remove();
        }
        else {
            $.ajax({

                data: "dicName=" + encodeURIComponent(encodeURIComponent(dicname)) + "&currentPath=" + encodeURIComponent(encodeURIComponent(currentPath)),
                url: "CreateDicController/createDic",
                success: function (data) {
                    refreshPage();
                    $(currentTrDiv).find("img[class='img-ok']").hide();
                    $(currentTrDiv).find("img[class='img-remove']").hide();
                },
                error: function () {
                    alert("出错啦，请重试......");
                },
            });

        }

    });

    $(currentTrDiv).find("img[class='img-remove']").on("click", function () {
        //删除当前添加的tr或者div
        $(removeTrDiv).remove();
    });


}




