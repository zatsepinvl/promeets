(function ($) {

    $.fn.ncDesk = function () {

        if (!$(this).data('ncDesk')) {
            $(this).data('ncDesk', new NcDesk($(this)[0]));
        }
        return $(this).data('ncDesk');

        //end jquery

        function NcDesk(canvas) {
            var settings = {
                stickerRectColor: "#0a0",
                stickerFont: 20,
                stickerFontFamily: 'Arial',
                stickerFontColor: "#000",
                stickerRectMargin: 10,
                stickerWidth: 200,
                stickerUsernameColor: "#ccc",
                stickerUsernameFontSize: 10
            };
            //constructor

            this._canvas = new fabric.Canvas(canvas);

            //end constructor


            var Sticker = fabric.util.createClass(fabric.Group, {

                type: 'sticker',

                initialize: function (text, username) {

                    var margin = settings.stickerRectMargin;

                    var itext = new fabric.IText(text, {
                        fill: settings.stickerFontColor,
                        fontSize: settings.stickerFont,
                        left: margin,
                        top: margin,
                        fontFamily: settings.stickerFontFamily,
                        width: settings.stickerWidth,
                    });
                    var usernameObj = new fabric.IText(username, {
                        fill: settings.stickerUsernameColor,
                        fontSize: settings.stickerUsernameFontSize,
                        fontFamily: settings.stickerFontFamily,
                    });

                    var rect = new fabric.Rect({
                        fill: settings.stickerRectColor,
                        left: itext.left - margin,
                        top: itext.top - margin,
                        width: itext.width + margin * 2,
                        height: itext.height + margin * 2 + 15,
                    });

                    this.callSuper('initialize', [rect, itext], {});

                    //usernameObj.scaleToHeight(settings.stickerUsernameHeight);
                    this.scaleToWidth(settings.stickerWidth);
                    this.add(usernameObj);
                    usernameObj.setLeft(rect.getWidth - usernameObj.getWidth());
                    usernameObj.setTop(rect.getHeight());


                },

                toObject: function () {
                    return fabric.util.object.extend(this.callSuper('toObject'), {
                        text: this.get('text')
                    });
                },

                _render: function (ctx) {
                    this.callSuper('_render', ctx);
                    ctx.strokeText(this.text, -this.width / 2, -this.height / 2 + 20);
                }
            });

            this.addTextSticker = function (text, username) {
                var sticker = new Sticker(text, username);
                this._canvas.add(sticker);
            };

        }
    }

})(jQuery);