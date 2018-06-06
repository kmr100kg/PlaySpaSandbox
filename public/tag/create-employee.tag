<create-employee>
    <div class="ui container default-margin-top">
        <h1>
            社員登録
        </h1>
        <div class="ui pointing menu">
            <a class="item active" data-tab="first">基本情報</a>
            <a class="item" data-tab="second">詳細情報</a>
        </div>
        <div class="ui bottom attached tab active" data-tab="first">
            <form class="ui form user-form">
                <div class="two fields">
                    <div class="field" id="employeeNumberField">
                        <label>社員番号<span class="required-input">*</span></label>
                        <input placeholder="半角数字" type="text" name="employeeNumber" value={getPreparedData('nextEmployeeNumber')}>
                        <div class="ui pointing red basic label" if={isErrorField("employeeNumber")}>
                            <virtual each={e in errors["employeeNumber"]}>
                                <p>{e}</p>
                            </virtual>
                        </div>
                    </div>
                    <div class="field" id="nameField">
                        <label>名前<span class="required-input">*</span></label>
                        <input placeholder="サンプル太郎" type="text" name="name" id="name">
                        <div class="ui pointing red basic label" if={isErrorField("name")}>
                            <virtual each={e in errors["name"]}>
                            <p>{e}</p>
                            </virtual>
                        </div>
                    </div>
                </div>
                <div class="two fields">
                    <div class="field" id="kanaField">
                        <label>カナ<span class="required-input">*</span></label>
                        <input placeholder="サンプルタロウ" type="text" name="kana" id="kana">
                        <div class="ui pointing red basic label" if={isErrorField("kana")}>
                            <virtual each={e in errors["kana"]}>
                            <p>{e}</p>
                            </virtual>
                        </div>
                    </div>
                    <div class="field" id="mailAddressField">
                        <label>メールアドレス<span class="required-input">*</span></label>
                        <input placeholder="hoge@sample.co.jp" type="text" name="mailAddress">
                        <div class="ui pointing red basic label" if={isErrorField("mailAddress")}>
                            <virtual each={e in errors["mailAddress"]}>
                            <p>{e}</p>
                            </virtual>
                        </div>
                    </div>
                </div>
                <div class="two fields">
                    <div class="field" id="passwordField">
                        <label>パスワード<span class="required-input">*</span></label>
                        <input placeholder="password" type="password" name="password">
                        <div class="ui pointing red basic label" if={isErrorField("password")}>
                            <virtual each={e in errors["password"]}>
                            <p>{e}</p>
                            </virtual>
                        </div>
                    </div>
                    <div class="field" id="passwordConfirmField">
                        <label>パスワード（確認）<span class="required-input">*</span></label>
                        <input placeholder="password" type="password" name="passwordConfirm">
                        <div class="ui pointing red basic label" if={isErrorField("passwordConfirm")}>
                            <virtual each={e in errors["passwordConfirm"]}>
                            <p>{e}</p>
                            </virtual>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div class="ui bottom attached tab" data-tab="second">
        <div class="ui form">
            <div class="field">
                <label>性別</label>
                <div class="inline fields">
                    <div class="ui selection dropdown" id="gender">
                        <input type="hidden" name="gender">
                        <i class="dropdown icon"></i>
                        <div class="default text">Gender</div>
                        <div class="menu">
                            <div class="item" data-value="1">男性</div>
                            <div class="item" data-value="0">女性</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        </div>
        <button class="ui button primary" onclick={create}>登録</button>
    </div>

    <script>
        const self = this

        self.on("mount", function () {
            self.prepareCreate()

            $('.menu .item').tab();

            $('#gender').dropdown({
                allowAdditions: true
            });

            $(function() {
                $.fn.autoKana('#name', '#kana', {
                    katakana : true
                });
            });
        })

        prepareCreate()
        {
            $.ajax({
                type: 'GET',
                url: '/prepareCreate',
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (data) {
                self.preparedData = data
            }).fail(function (xhr) {
                self.errors = self.globalErrorHandler.handle(xhr)
            }).always(function () {
                self.update()
            })
        }

        create()
        {
            self.removeErrorClass()
            const userForm = {}
            $('input[name]').toArray().forEach(function (input) {
                const name = input.name
                userForm[name] = input.value
            })
            $.ajax({
                type: 'POST',
                url: '/create',
                contentType: 'application/json',
                data: JSON.stringify(userForm)
            }).done(function (data) {
                self.globalMessage.add('createdEmployee', data.successes)
                route('/list')
            }).fail(function (xhr) {
                self.dispMessage(self.globalErrorHandler.handle(xhr))
            })
        }

        dispMessage(msg)
        {
            self.errors = msg
            for (key in self.errors) {
                $('#' + key + 'Field').addClass('error')
            }
            self.update()
        }

        removeErrorClass()
        {
            if (self.errors !== null && self.errors !== undefined) {
                for (key in self.errors) {
                    $('#' + key + 'Field').removeClass('error')
                }
            }
        }

        getPreparedData(key)
        {
            const data = self.preparedData
            if (data === null || data === undefined) {
                return ""
            }
            return data[key]
        }

        isErrorField(key)
        {
            if (self.errors !== null && self.errors !== undefined && self.errors[key] !== undefined) {
                return true
            } else {
                return false
            }
        }

    </script>
</create-employee>
