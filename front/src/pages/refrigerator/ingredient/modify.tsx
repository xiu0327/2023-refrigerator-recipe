import { useState } from "react";
import router from "next/router";
import { Button } from "react-bootstrap";

import BackLayout from "@/components/layout/BackLayout";
import Label from "@/components/global/Label";
import StorageTab from "@/components/refrigerator/StorageTab";
import DateInput from "@/components/refrigerator/DateInput";
import VolumeInput from "@/components/refrigerator/VolumeInput";
import BottomBtn from "@/components/global/BottomBtn";

export default function AddIngredientPage() {
	// 기존 식재료 값 넣어야됨
	const [storage, setStorage] = useState(0);
	const [expiredDate, setExpiredDate] = useState(new Date());
	const [volume, setVolume] = useState(3);

	return (
		<BackLayout>
			<div className="d-flex flex-column flex-fill">
				<div className="p-4 pt-5 d-flex flex-column flex-fill gap-5">
					<div>
						<div className="d-flex gap-1">
							<h1 className="flex-fill">사과</h1>
							<Button disabled>수정 중</Button>
						</div>
						<div className="text-muted">2023년 01월 01일 등록</div>
					</div>

					<div className="d-flex flex-column gap-4">
						<Label label="보관 방법">
							<StorageTab setStorage={setStorage} />
						</Label>
						<Label label="소비기한">
							<DateInput date={expiredDate} setDate={setExpiredDate} />
						</Label>
						<Label label="용량">
							<VolumeInput setVolume={setVolume} unit="개" />
						</Label>
					</div>
				</div>

				<BottomBtn
					label="추가하기"
					onClick={() => router.back()}
					// 값다입력했는지확인하고,식재료수정하고,토스트메시지띄우기,라우트백
				/>
			</div>
		</BackLayout>
	);
}
