<user-detail-info>
    <div class="ui form">
        <div class="two fields">
            <div class="three wide field">
                <label>性別<span class="required-input">*</span></label>
                <div class="ui selection dropdown" id="gender">
                    <input type="hidden" name="gender">
                    <i class="dropdown icon"></i>
                    <div class="default text">性別</div>
                    <div class="menu">
                        <div class="item" data-value="1">男性</div>
                        <div class="item" data-value="0">女性</div>
                        <div class="item" data-value="2">ひ・み・つ</div>
                    </div>
                </div>
            </div>
            <div class="three wide field">
                <label>生年月日<span class="required-input">*</span></label>
                <div class="ui calendar" id="calendar1">
                    <div class="ui input left icon">
                        <i class="calendar icon"></i>
                        <input type="text" placeholder="yyyy/MM/dd" name="birthday">
                    </div>
                </div>
            </div>
        </div>
        <div class="two fields">
            <div class="three wide field">
                <label>郵便番号<span class="required-input">*</span></label>
                <input placeholder="1234567" type="text" name="zip"
                       onKeyUp="AjaxZip3.zip2addr(this,'','address','address');">
            </div>
            <div class="thirteen wide field">
                <label>住所（番地まで入力してください）<span class="required-input">*</span></label>
                <input placeholder="東京都港区芝公園４丁目２−８" type="text" name="address">
            </div>
        </div>
        <div class="field">
            <label>備考</label>
            <textarea class="ui text-area"></textarea>
        </div>
    </div>

    <script>
        const self = this

        self.on("mount", function () {
            $('#gender').dropdown({
                allowAdditions: true
            });

            $(function() {
                $.fn.autoKana('#name', '#kana', {
                    katakana : true
                });
            });

            $('#calendar1').calendar({
                type: 'date',
                initialDate: new Date('1990/01/01'),
                startMode: 'year',
                text: {
                    days: ['日', '月', '火', '水', '木', '金', '土'],
                    months: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
                    monthsShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
                },
                formatter: {
                    date: function (date) {
                        var day = ('0' + date.getDate()).slice(-2);
                        var month = ('0' + (date.getMonth() + 1)).slice(-2);
                        var year = date.getFullYear();
                        return year + '-' + month + '-' + day;
                    }
                }
            });
        })

    </script>
</user-detail-info>
