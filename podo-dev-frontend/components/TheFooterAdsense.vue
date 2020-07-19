<template>
    <div>
        <client-only>
            <InFeedAdsense
                root-class="wrapper"
                data-ad-format="fluid"
                data-ad-layout-key="-gk+2d+39-5t+2g"
                data-ad-client="ca-pub-3301697379976707"
                data-ad-slot="3058698679"
            ></InFeedAdsense>
        </client-only>
    </div>
</template>

<script>
export default {
    name: "TheFooterAdsense"
};
</script>

<style lang="scss" scoped>
.wrapper {
    max-width: var(--wild-max-width);
    max-height: 300px;
    margin: 0 auto 30px auto;
}
</style>
