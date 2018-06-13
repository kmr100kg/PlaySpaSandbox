<position-selector>
    <label>役職<span class="required-input">*</span></label>
    <div class="ui fluid selection dropdown" id="position">
        <input type="hidden" name="position">
        <i class="dropdown icon"></i>
        <div class="default text">役職</div>
        <div class="menu">
            <virtual each={p in position}>
                <div class="item" data-value={p.name}>{p.id} : {p.name}</div>
            </virtual>
        </div>
    </div>

    <script>
        const self = this

        self.on("mount", function () {
            $.ajax({
                type: 'GET',
                url: '/position/list',
                contentType: 'application/json',
                dataType: 'json'
            }).done(function (data) {
                self.position = data
            }).fail(function (xhr) {
                self.errors = self.globalErrorHandler.handle(xhr)
            }).always(function () {
                self.update()
            })
        })

    </script>
</position-selector>