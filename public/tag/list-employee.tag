<list-employee>
    <div class="ui container default-margin-top">
        <h1>
            社員一覧
        </h1>
        <div class="ui success message" id="successMsgBox" show={successes}>
            <i class="close icon"></i>
            <div class="header">
                Completed
            </div>
            <p>{successes}</p>
        </div>
        <div class="ui success message" id="errorMsgBox" show={errors}>
            <i class="close icon"></i>
            <div class="header">
                Failed
            </div>
            <p>{errors}</p>
        </div>
        <table class="ui very basic table">
            <tr>
                <th>社員番号</th>
                <th>氏名</th>
                <th>カナ</th>
                <th>メールアドレス</th>
                <th>パスワード</th>
                <th>&nbsp;</th>
            </tr>
            <tr each={emp in employees}>
                <td>{ emp.employeeNumber }</td>
                <td>{ emp.name }</td>
                <td>{ emp.kana }</td>
                <td>{ emp.mailAddress }</td>
                <td>{ emp.password }</td>
                <td class="right aligned">
                    <div class="tiny ui green button" onclick={edit}>編集</div>
                    <div class="tiny ui red button" emp-no={emp.employeeNumber} emp-name={emp.name} onclick={delConfirm}>削除</div>
                </td>
            </tr>
        </table>
    </div>

    <div class="ui tiny modal" id="deleteModal">
        <div class="header">削除</div>
        <div class="content">
            <p>以下の社員を削除しますか？</p>
            <table class="ui very basic table">
                <tr>
                    <th>社員番号</th>
                    <th>氏名</th>
                </tr>
                <tr>
                    <td>{empNo}</td>
                    <td>{empName}</td>
                </tr>
            </table>
        </div>
        <div class="actions">
            <div class="ui negative button">いいえ</div>
            <div class="ui positive right labeled icon button" emp-no={empNo} onclick={del}>
                はい
                <i class="checkmark icon"></i>
            </div>
        </div>
    </div>

    <script>
        const self = this

        self.on("mount", function () {
            self.list()
            self.dispGlobalMessage()
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
                self.update()
            }).fail(function (xhr) {
                self.dispMessage('fail', self.globalErrorHandler.handle(xhr))
            })
        }

        edit()
        {

        }

        delConfirm(e)
        {
            self.empNo = e.currentTarget.getAttribute('emp-no')
            self.empName = e.currentTarget.getAttribute('emp-name')
            self.update()
            $('#deleteModal').modal({
                // マルチディスプレイ使用時でもズレなくなる
                observeChanges: true
            }).modal('show');
        }

        del(e)
        {
            const employeeNumber = e.currentTarget.getAttribute('emp-no')
            $.ajax({
                type: 'GET',
                url: '/delete?employeeNumber=' + employeeNumber,
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (data) {
                self.list()
                self.dispMessage('success', data.successes)
            }).fail(function (xhr) {
                self.dispMessage('fail', self.globalErrorHandler.handle(xhr))
            })
        }

        dispMessage(msgType, message)
        {
            if (msgType === 'success') {
                self.successes = message
                self.update()
                $('#successMsgBox').transition({
                    animation: 'fade in',
                    duration: '2s'
                });
            } else {
                self.errors = message
                self.update()
                $('#errorMsgBox').transition({
                    animation: 'fade in',
                    duration: '2s'
                });
            }
        }

        dispGlobalMessage()
        {
            const msg = self.globalMessage.take('createdEmployee')
            if (msg !== undefined) {
                self.dispMessage('success', msg)
            }
        }
    </script>
</list-employee>