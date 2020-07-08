<template>
    <div id="wrapHistories">
        <div v-for="(history, index) in histories" v-bind:key="history.id">
            <div @click="fetchHistory(history.id)" class="history">
                <div class="title">{{ index + 1 }}. {{ history.title }}</div>
                <div class="create-at">{{ history.createAt }}</div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "BlogPostHistory",
    props: {
        histories: Array
    },
    methods: {
        fetchHistory(historyId) {
            this.$emit("fetch", historyId);
        }
    }
};
</script>

<style lang="scss" scoped>
#wrapHistories {
    margin-top: 10px;
}

.history {
    display: flex;
    justify-content: space-between;
    cursor: pointer;
    color: #444444;
    font-size: 0.85rem;

    &:hover {
        text-decoration: underline;
    }

    .title {
        flex: 1;
    }

    .create-at {
    }
}
</style>
