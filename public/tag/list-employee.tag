<list-employee>
    <div class="ui container">
        <h1>
            社員一覧
        </h1>
        <table class="ui basic table">
            <tr>
                <th>社員番号</th>
                <th>氏名</th>
                <th>カナ</th>
                <th>メールアドレス</th>
                <th>パスワード</th>
            </tr>
            <tr each={emp in employees}>
                <td>{ emp.employeeNumber }</td>
                <td>{ emp.name }</td>
                <td>{ emp.kana }</td>
                <td>{ emp.mailAddress }</td>
                <td>{ emp.password }</td>
            </tr>
        </table>
    </div>

    <script>
        const self = this

        self.on("mount", function () {
            console.log("aaa")
            self.list()
        })

        list()
        {
            $.ajax({
                type: 'GET',
                url: '/list',
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (data) {
                self.employees = data
            }).fail(function (xhr) {
                self.errors = JSON.parse(xhr.responseText)["errors"]
            }).always(function () {
                self.update()
            })
        }
    </script>
</list-employee>