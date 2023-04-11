import router from "next/router";
import { Button } from "react-bootstrap";

import BackLayout from "@/components/layout/BackLayout";
import Label from "@/components/global/Label";
import FixedForm from "@/components/refrigerator/FixedForm";

export default function IngredientInfoPage() {
	return (
		<BackLayout>
			<div className="p-4 pt-5 d-flex flex-column flex-fill gap-5">
				<div>
					<div className="d-flex gap-1">
						<h1 className="flex-fill">사과</h1>
						<Button onClick={() => router.push("/refrigerator/ingredient/modify")}>📝</Button>
						<Button onClick={() => "삭제확인모달띄우고삭제API호출"}>🗑️</Button>
					</div>
					<div className="text-muted">2023년 01월 01일 등록</div>
				</div>

				<div className="d-flex flex-column gap-4">
					<Label label="보관 방법">
						<FixedForm value="냉동" />
					</Label>
					<Label label="소비기한" sub="D-30">
						<FixedForm value="2021-01-01" />
					</Label>
					<Label label="용량">
						<FixedForm value="3" label="개" />
					</Label>
				</div>
			</div>
		</BackLayout>
	);
}
