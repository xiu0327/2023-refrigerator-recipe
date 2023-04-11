import { useState } from "react";
import router from "next/router";

import BackLayout from "@/components/layout/BackLayout";
import BottomBtn from "@/components/global/BottomBtn";
import Label from "@/components/global/Label";
import StorageTab from "@/components/refrigerator/StorageTab";
import DateInput from "@/components/refrigerator/DateInput";
import VolumeInput from "@/components/refrigerator/VolumeInput";

export default function AddIngredientInfoPage() {
	const [storage, setStorage] = useState(0);
	const [expiredDate, setExpiredDate] = useState<Date>(new Date());
	const [volume, setVolume] = useState<number | null>();

	return (
		<BackLayout>
			<div className="d-flex flex-column flex-fill">
				<div className="p-4 pt-5 d-flex flex-column flex-fill gap-5">
					<div>
						<h1>사과</h1>
						<div className="text-muted">추가할 식재료에 대해 알려주세요</div>
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
					// 값다입력했는지확인하고,식재료추가하고,토스트메시지띄우기,라우트백
				/>
			</div>
		</BackLayout>
	);
}
