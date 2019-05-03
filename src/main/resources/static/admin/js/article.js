
// Tags Input
$('#tags').tagsInput({
    width: '100%',
    height: '35px',
    defaultText: '请输入文章标签'
});

$('.toggle').toggles({
    on: true,
    text: {
        on: '开启',
        off: '关闭'
    }
});

$(".select2").select2({
    width: '100%'
});

var tale = new $.tale();

/**
 * 保存文章
 * @param status publish or draft
 */
function subArticle(status) {
    //在 #articleForm中 查询input输入框 name=title的元素
    var title = $('#articleForm input[name=title]').val();
    var content = $('#text').val();
    if (title == '') {
        tale.alertWarn('请输入文章标题');
        return;
    }
    if (content == '') {
        tale.alertWarn('请输入文章内容');
        return;
    }
    //赋值
    $('#content-editor').val(content);
    //publish或者 draft
    $("#articleForm #status").val(status);
    //分类
    $("#articleForm #" +
        "categories").val($('#multiple-sel').val());
    var params = $("#articleForm").serialize();
    //修改或者发布
    var url = $('#articleForm #cid').val() != '' ? '/admin/article/modify' : '/admin/article/publish';
    tale.post({
        url:url,
        data:params,
        success: function (result) {
            if (result && result.success) {
                tale.alertOk({
                    text:'文章保存成功',
                    then: function () {
                        setTimeout(function () {
                            window.location.href = '/admin/article';
                        }, 500);
                    }
                });
            } else {
                tale.alertError(result.msg || '保存文章失败');
            }
        }
    });
}
var textarea = $('#text'),
    toolbar = $('<div class="markdown-editor" id="md-button-bar" />').insertBefore(textarea.parent())
preview = $('<div id="md-preview" class="md-hidetab" />').insertAfter('.markdown-editor');

markdown(textarea, toolbar, preview);


function allow_comment(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_comment').val(false);
    }
    if (off == 1) {
        $('#allow_comment').val(true);
    }
}

function allow_stick(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_stick').val(false);
    }
    if (off == 1) {
        $('#allow_stick').val(true);
    }
}


function allow_feed(obj) {
    var this_ = $(obj);
    var on = this_.find('.toggle-on.active').length;
    var off = this_.find('.toggle-off.active').length;
    if (on == 1) {
        $('#allow_feed').val(false);
    }
    if (off == 1) {
        $('#allow_feed').val(true);
    }
}

$('div.allow-false').toggles({
    off: true,
    text: {
        on: '开启',
        off: '关闭'
    }
});