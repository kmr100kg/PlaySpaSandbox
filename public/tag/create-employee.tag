<create-employee>
    <div class="ui container">
        <h1>
            社員登録
        </h1>
        <form class="ui form error user-form">
            <div class="ui error message" show={errors}>
                <virtual each={e in errors}>
                    <div class="header">{e[0]}</div>
                    <p>{e[1]}</p>
                </virtual>
            </div>
            <div class="two fields">
                <div class="field">
                    <label>社員番号</label>
                    <input placeholder="半角数字" type="text" name="employeeNumber">
                </div>
                <div class="field">
                    <label>名前</label>
                    <input placeholder="サンプル太郎" type="text" name="name">
                </div>
            </div>
            <div class="two fields">
                <div class="field">
                    <label>カナ</label>
                    <input placeholder="サンプルタロウ" type="text" name="kana">
                </div>
                <div class="field">
                    <label>メールアドレス</label>
                    <input placeholder="hoge@sample.co.jp" type="text" name="mailAddress">
                </div>
            </div>
            <div class="two fields">
                <div class="field">
                    <label>パスワード(半角英数記号8文字以上)</label>
                    <input placeholder="password" type="password" name="password">
                </div>
                <div class="field">
                    <label>パスワード(確認)</label>
                    <input placeholder="password" type="password" name="passwordConfirm">
                </div>
            </div>
        </form>
        <button class="ui button primary" onclick={create}>登録</button>
    </div>

    <script>
        const self = this
        create()
        {
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
                alert("登録しました！")
                route('/list')
            }).fail(function (xhr) {
                self.errors = JSON.parse(xhr.responseText)["errors"]
            }).always(function () {
                self.update()
            })
        }
    </script>
</create-employee>