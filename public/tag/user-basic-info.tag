<basic-user-info>
    <form class="ui form user-form">
        <div class="two fields">
            <div class="field" id="employeeNumberField">
                <label>社員番号<span class="required-input">*</span></label>
                <input placeholder="半角数字" type="text" name="employeeNumber" value={getPreparedData('nextEmployeeNumber')}>
                <div class="ui pointing red basic label" if={isErrorField("employeeNumber")}>
                    <virtual each={e in parent.errors["employeeNumber"]}>
                    <p>{e}</p>
                    </virtual>
                </div>
            </div>
            <div class="field" id="nameField">
                <label>名前<span class="required-input">*</span></label>
                <input placeholder="サンプル太郎" type="text" name="name" id="name">
                <div class="ui pointing red basic label" if={isErrorField("name")}>
                    <virtual each={e in parent.errors["name"]}>
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
                    <virtual each={e in parent.errors["kana"]}>
                    <p>{e}</p>
                    </virtual>
                </div>
            </div>
            <div class="field" id="mailAddressField">
                <label>メールアドレス<span class="required-input">*</span></label>
                <input placeholder="hoge@sample.co.jp" type="text" name="mailAddress">
                <div class="ui pointing red basic label" if={isErrorField("mailAddress")}>
                    <virtual each={e in parent.errors["mailAddress"]}>
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
                    <virtual each={e in parent.errors["password"]}>
                    <p>{e}</p>
                    </virtual>
                </div>
            </div>
            <div class="field" id="passwordConfirmField">
                <label>パスワード（確認）<span class="required-input">*</span></label>
                <input placeholder="password" type="password" name="passwordConfirm">
                <div class="ui pointing red basic label" if={isErrorField("passwordConfirm")}>
                    <virtual each={e in parent.errors["passwordConfirm"]}>
                    <p>{e}</p>
                    </virtual>
                </div>
            </div>
        </div>
    </form>

    <script>
        const self = this

        self.on("mount", function() {
            self.prepareCreate()
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
            if (self.parent.errors !== null && self.parent.errors !== undefined && self.parent.errors[key] !== undefined) {
                return true
            } else {
                return false
            }
        }

    </script>
</basic-user-info>
