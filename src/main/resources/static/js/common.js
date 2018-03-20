var $MB = (function() {
    var bootstrapTable_default = {
            method: 'get',
            striped: true,
            cache: false,
            pagination: true,
            sortable: false,
            sidePagination: "server",
            pageNumber: 1,
            pageSize: 10,
            pageList: [5, 10, 25, 50, 100],
            strictSearch: true,
            showColumns: false,
            minimumCountColumns: 2,
            clickToSelect: true,
            uniqueId: "ID",
            cardView: false,
            detailView: false,
            smartDisplay: false,
            queryParams: function(params) {
                return {
                    pageSize: params.limit,
                    pageNum: params.offset / params.limit + 1,
                };
            }
        }
    function _initTable(id, settings) {
        var params = $.extend({}, bootstrapTable_default, settings);
        if (typeof params.url == 'undefined') {
            throw '初始化表格失败，请配置url参数！';
        }
        if (typeof params.columns == 'undefined') {
            throw '初始化表格失败，请配置columns参数！';
        }
        $('#' + id).bootstrapTable(params);
        $("body").on("click", "[data-table-action]", function(a) {
            a.preventDefault();
            var b = $(this).data("table-action");
            if ("excel" === b && $(this).closest(".dataTables_wrapper").find(".buttons-excel").trigger("click"), "csv" === b && $(this).closest(".dataTables_wrapper").find(".buttons-csv").trigger("click"), "print" === b && $(this).closest(".dataTables_wrapper").find(".buttons-print").trigger("click"), "fullscreen" === b) {
                var c = $(this).closest(".card");
                c.hasClass("card--fullscreen") ? (c.removeClass("card--fullscreen"), $("body").removeClass("data-table-toggled")) : (c.addClass("card--fullscreen"), $("body").addClass("data-table-toggled"))
            }
        })
    }
    // jquery treegird
    function _initTreeTable(id, setting) {
        $('#' + id).bootstrapTreeTable({
            id: setting.id, // 选取记录返回的值
            code: setting.code, // 用于设置父子关系
            parentColumn: !setting.parentColumn ? 'parentId' : setting.parentColumn, // 用于设置父子关系
            type: "GET", // 请求数据的ajax类型
            url: setting.url, // 请求数据的ajax的url
            ajaxParams: !setting.ajaxParams ? {} : setting.ajaxParams, // 请求数据的ajax的data属性
            expandColumn: !setting.expandColumn ? '1' : setting.expandColumn, // 在哪一列上面显示展开按钮
            striped: true, // 是否各行渐变色
            bordered: true,
            checkboxes: true,
            expandAll: setting.expandAll ? true : setting.expandAll, // 是否全部展开
            columns: setting.columns

        });
    }

    /*--------------------------------------
        Bootstrap Notify Notifications
    ---------------------------------------*/
    function _notify(message, type, element) {
        $.notify({
            icon: "fa fa-check",
            title: "",
            message: message,
            url: ''
        }, {
            element: element == null ? 'body' : element,
            type: type,
            allow_dismiss: true,
            placement: {
                from: "top",
                align: "center"
            },
            offset: {
                x: 20,
                y: 20
            },
            spacing: 10,
            z_index: 1031,
            delay: 2500,
            timer: 1000,
            url_target: '_blank',
            mouse_over: false,
            animate: {
                enter: "animated fadeInDown",
                exit: "animated fadeOutUp"
            },
            template: '<div data-notify="container" class="alert alert-dismissible alert-{0} alert--notify" role="alert">' +
                '<span data-notify="icon"></span> ' +
                '<span data-notify="title">{1}</span> ' +
                '<span data-notify="message" style="font-weight: 600">{2}</span>' +
                '<div class="progress" data-notify="progressbar">' +
                '<div class="progress-bar progress-bar-{0}" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width: 0%;"></div>' +
                '</div>' +
                '<a href="{3}" target="{4}" data-notify="url"></a>' +
                '<button type="button" aria-hidden="true" data-notify="dismiss" class="alert--notify__close"><span style="background-color: rgba(255,255,255,.2);line-height: 19px;height: 20px;width: 20px;border-radius: 50%;font-size: 1.1rem;display: block;" aria-hidden="true">×</span></button>' +
                '</div>'
        });
    }

    // date string format
    Date.prototype.Format = function(fmt) {
        var o = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "h+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3),
            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ?
                    (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }

    // close modal
    function _closeModal(modalId) {
        $("#" + modalId).find("button.btn-hide").attr("data-dismiss", "modal").trigger('click');
    }

    // close and reset modal
    function _closeAndRestModal(modalId) {
        var $modal = $("#" + modalId);
        $modal.find("button.btn-hide").attr("data-dismiss", "modal").trigger('click');
        $modal.find("form")[0].reset();
    }

    // 获取主题对应的16进制颜色
    function _getThemeColor(theme) {
        var color;
        switch (theme) {
            case 'green':
                color = '#32c787';
                break;
            case 'blue':
                color = '#2196F3';
                break;
            case 'red':
                color = '#ff5652';
                break;
            case 'orange':
                color = '#FF9800';
                break;
            case 'teal':
                color = '#39bbb0';
                break;
            case 'cyan':
                color = '#00BCD4';
                break;
            case 'blue-grey':
                color = '#607D8B';
                break;
            case 'purple':
                color = '#d559ea';
                break;
            case 'indigo':
                color = '#3F51B5';
                break;
            case 'lime':
                color = '#CDDC39';
                break;
            default:
                color = '#32c787';
        }
        return color;
    }

    // confirm弹窗
    function _confirm(settings, callback) {
        swal({
            text: settings.text,
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: settings.confirmButtonText,
            cancelButtonText: "取消",
            allowOutsideClick: false,
            allowEscapeKey: false,
            animation: false
        }).then(callback);
    }

    // 恢复jsTree，还原到初始化状态
    function _resetJsTree(id) {
        $('#' + id).jstree(true).close_all();
        $('#' + id).jstree(true).deselect_all();
    }

    // 重新加载数据，重绘jsTree
    function _refreshJsTree(id, fn) {
        $('#' + id).data('jstree', false).empty();
        fn;
    }

    return {
        initTable: function(id, setting) {
            _initTable(id, setting);
        },
        initTreeTable: function(id, setting) {
            _initTreeTable(id, setting);
        },
        getTableIndex: function(id, index) {
            var pageSize = $('#' + id).bootstrapTable('getOptions').pageSize;
            var pageNumber = $('#' + id).bootstrapTable('getOptions').pageNumber;
            return pageSize * (pageNumber - 1) + index + 1;
        },
        refreshTable: function(id) {
            $('#' + id).bootstrapTable('refresh');
        },
        n_default: function(message, element) {
            _notify(message, "inverse", element);
        },
        n_info: function(message, element) {
            _notify(message, "info", element);
        },
        n_success: function(message, element) {
            _notify(message, "success", element);
        },
        n_warning: function(message, element) {
            _notify(message, "warning", element);
        },
        n_danger: function(message, element) {
            _notify(message, "danger", element);
        },
        dateFormat: function(date, format) {
            return new Date(date).Format(format);
        },
        closeModal: function(modalId) {
            _closeModal(modalId);
        },
        closeAndRestModal: function(modalId) {
            _closeAndRestModal(modalId);
        },
        getThemeColor: function(theme) {
            return _getThemeColor(theme);
        },
        confirm: function(settings, callback) {
            _confirm(settings, callback);
        },
        resetJsTree: function(id) {
            _resetJsTree(id);
        },
        refreshJsTree: function(id, fn) {
            _refreshJsTree(id, fn);
        }
    }
})($);