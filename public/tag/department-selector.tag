<department-selector>
    <label>部門<span class="required-input">*</span></label>
    <div class="ui fluid selection dropdown" id="department">
        <input type="hidden" name="department">
        <i class="dropdown icon"></i>
        <div class="default text">部門</div>
        <div class="menu">
            <virtual each={d in department}>
                <div class="item" data-value={d.name}>{d.id} : {d.name}</div>
            </virtual>
        </div>
    </div>

    <script>
        const self = this

        self.on("mount", function () {
            $.ajax({
                type: 'GET',
                url: '/department/list',
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (data) {
                self.department = data
            }).fail(function (xhr) {
                self.errors = self.globalErrorHandler.handle(xhr)
            }).always(function () {
                self.update()
            })
        })

    </script>
</department-selector>