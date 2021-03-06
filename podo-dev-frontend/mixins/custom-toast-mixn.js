/**
 * 공통으로 사용되는 커스텀 Toast.
 */

export default {
    methods: {
        /**
         * 삭제 확인 메소드
         */
        toastConfirm(description, callbackOk, callbackNo) {
            this.$toast.show(description, {
                keepOnHover: true,
                duration: 5000,
                action: [
                    {
                        text: "아니오",
                        onClick: (e, toastObject) => {
                            if (callbackNo) {
                                callbackNo();
                            }
                            toastObject.goAway(0);
                        }
                    },
                    {
                        text: "네",
                        onClick: (e, toastObject) => {
                            if (callbackOk) {
                                callbackOk();
                            }
                            toastObject.goAway(0);
                        }
                    }
                ]
            });
        }
    }
};
