<template>
    <div id="wrapBlog">
        <div id="head">
            <div id="tags">
                <span>#태그1</span>
                <span>#태그1</span>
            </div>

            <div id="title">
                Spring JPA 공부하기
            </div>

            <div id="info">
                <span>2019-05-01</span>
                <span>조회수 0</span>
            </div>
        </div>


        <div id="submenus">
            <span>수정</span>
            <span>삭제</span>
            <span>공유하기</span>
            <span>목록</span>
            <span>이전글</span>
            <span>다음글</span>
        </div>

        <div id="contents">
            <h3>LSP, Liskov Substitution Principle<br/>
                (리스코프 치환 원칙)</h3>

            <p>일반화 관계에서&nbsp;자식 클래스는 최소한 자신의 부모클래스에서 가능한 행위는 수행 할 수 있어야 한다.<br/>
                부모클래스와 자식클래스 사이에 행위가 일관성 있어야 한다.</p>

            <p><br/>
                &nbsp;</p>

            <h3>#Before branch</h3>

            <p><img alt="" src="/uploaded/study/image/KEQQMN181224235521.JPG" style="height:260px; width:140px"/></p>

            <p>MinMax1은 MinMax를 상속받는다.</p>

            <p>&nbsp;</p>

            <p>&nbsp;</p>

            <p>&nbsp;</p>

            <p><strong>MinMax.java</strong></p>

            <pre>
<code class="language-java">import java.util.ArrayList;
import java.util.Collections;

public class MinMax{

	public ArrayList mimax(ArrayList a){
		int minValue;
		int maxValue;
		ArrayList b;

		minValue = Collections.min(a);
		maxValue = Collections.max(a);

		a.set(0, minValue);
		a.set(a.size() - 1, maxValue);

		b=a;

		return b;

	}
}
</code></pre>

            <p>MinMax는 배열에서 가장 큰값과, 가장 작은 값을 찾아서<br/>
                가장 작은 값은 0번째 순서에,<br/>
                가장 큰값은 마지막 순서에 넣는다.<br/>
                <br/>
                <br/>
                <strong>MinMax1.java</strong></p>

            <pre>
<code class="language-java">import java.util.ArrayList;
import java.util.Collections;

public class MinMax1 extends MinMax {
	public ArrayList mimax(ArrayList a){
		int minValue;
		int maxValue;
		ArrayList b;

		minValue = Collections.min(a);
		maxValue = Collections.min(a);

		a.set(0, minValue);
		a.set(a.size() - 1, maxValue);

		b=a;

		return b;

	}
}
</code></pre>

            <p><br/>
                MinMax1은 배열에서 가장 작은 값을 찾아<br/>
                0번째 순서에, 마지막 순서에 넣는다.</p>

            <p>&nbsp;</p>

            <p><strong>위 프로그램은 LSP원칙을 위반함을 보여준다</strong></p>

            <p><br/>
                LSP 원칙은 일반화 관계를 설명한다<br/>
                일반화 관계는 is kind of 관계이다.<br/>
                그렇다면 MinMax1 is kind of MinMax 관계는 성립한다고 할 수 있을까?<br/>
                LSP 원칙에 맞추어 행위의 일관성에 대해서 판단하여야 한다.<br/>
                <br/>
                부모 클래스의 객체를 자식 클래스의 객체가 대신 할 수 있는지 확인하여야 한다.</p>

            <p>&nbsp;</p>

            <p>그렇다면 MinMax1클래스의 객체는 MinMax클래스의 객체를 대신 할 수 있을까?<br/>
                MinMax 클래스에서 배열의 마지막 자리는 최댓값이 들어 가지만,<br/>
                MinMax1 클래스에서 배열의 마지막 자리는 최솟값이 들어간다.<br/>
                MinMax의 객체가 MinMax1의 객체를 대신 할 수는 없을 것이다.<br/>
                그러므로 행위에 일관성이 없으며&nbsp;<br/>
                MinMax is kind of MinMax1관계는 성립 하지 않는다.</p>

            <p><br/>
                &nbsp;</p>

            <h3>#After branch</h3>

            <p><img alt="" src="/uploaded/study/image/32V27F181224235521.JPG" style="height:260px; width:140px"/></p>

            <p>MinMax2는 MinMax1을 상속 받는다.</p>

            <p><strong>위 프로그램은 LSP원칙을 위반하지 않음을 보여준다</strong>.</p>

            <p>&nbsp;</p>

            <p>&nbsp;</p>

            <p><strong>MinMax.java</strong></p>

            <pre>
<code class="language-java">import java.util.ArrayList;
import java.util.Collections;

public class MinMax{

	public ArrayList mimax(ArrayList a){
		int minValue;
		int maxValue;
		ArrayList b;

		minValue = Collections.min(a);
		maxValue = Collections.max(a);

		a.set(0,minValue);
		a.set(a.size()-1,maxValue);

		b=a;

		return b;

	}
}
</code></pre>

            <p>MinMax는 배열에서 가장 큰값과, 가장 작은 값을 찾아서<br/>
                가장 작은 값은 0번째 순서에,<br/>
                가장 큰값은 마지막 순서에 넣는다.<br/>
                <br/>
                <br/>
                <strong>MinMax2.java</strong></p>

            <pre>
<code class="language-java">import java.util.ArrayList;
import java.util.Collections;

public class MinMax2 extends MinMax {
	public ArrayList mimax(ArrayList a){
		int minValue;
		int maxValue;
		ArrayList b;

		Collections.sort(a);

		b=a;

		return b;

	}
}
</code></pre>

            <p>MinMax2 클래스는 배열을 정렬한다.</p>

            <p>위의 일반화 관계에서는 행위의 일관성이 있을까?<br/>
                MinMax 클래스에서 배열의 첫 자리에는 최솟값, 마지막 자리는 최댓값이 들어가고,<br/>
                MinMax2 클래스 또 한 정렬에 의해 배열의 첫 자리에, 최소값 가장 마지막 자리에 최댓값이 들어간다.<br/>
                MinMax2의 객체는 MinMax의 객체를 대신 할 수 있을 것이다.&nbsp;<br/>
                그러므로 행위의 일관성이 있으므로, MinMax2 is kind of MinMax 는 성립한다</p>


        </div>

    </div>
</template>

<script>
    export default {
        name: 'BlogVue',
    }
</script>

<style scoped>
    #wrapBlog{
        width: 800px;
        margin: 0px auto;
    }

    #head {
        margin: 150px 0;
    }

    #head #tags {
        margin-bottom: 15px;
        text-align: center;
        font-weight: bold;
        color: #ec5621;
        cursor: pointer;
    }

    #head #tags span {
        margin: 0px 5px;
    }

    #head #title {
        font-size: 2.5rem;
        margin-bottom: 15px;
        text-align: center;

        word-break: keep-all;
        word-wrap: break-word;
    }

    #head #info {
        display: flex;
        flex-flow: row nowrap;
        justify-content: center;
        align-items: center;
        font-size: 0.75rem;
        padding: 0 2px;
        color: #9199A4;
    }

    #head #info span {
        margin: 0px 5px;
    }

    #submenus {
        border-top: 1px solid #9199a4;
        border-bottom: 1px solid #ecf0f5;
        padding: 8px 0;

        display: flex;
        align-items: center;
        justify-content: flex-end;
    }

    #submenus span {
        margin-left: 10px;
    }

    #contents {
        margin-top: 50px;
    }
</style>
