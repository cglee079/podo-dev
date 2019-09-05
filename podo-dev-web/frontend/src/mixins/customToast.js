/**
 * 공통으로 사용되는 커스텀 Toast.
 */

export default {
    methods: {

        /**
         * 삭제 확인 메소드
         * @param description 부가설명
         * @param callback '예' 클릭 시 Callback
         */
        toastConfirm(description, callback) {

            this.$toasted.show(description, {
                    keepOnHover: true,
                    duration: 5000,
                    action: [
                        {
                            text: '아니오',
                            onClick: (e, toastObject) => {
                                toastObject.goAway(0);
                            }
                        },
                        {
                            text: '네',
                            onClick: (e, toastObject) => {
                                callback();
                                toastObject.goAway(0);
                            }
                        }
                    ]
                }
            )
        }

    },

}
