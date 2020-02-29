import bus from "../utils/bus";

export default function() {
    bus.$emit("scroll:unset-all");
}
