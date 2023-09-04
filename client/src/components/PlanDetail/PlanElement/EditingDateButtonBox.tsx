import styles from './EditingDateButtonBox.module.scss'

type EditingDateButtonBoxProps = {
  dateId: number
  handleEdit: (id: number) => void
  handleDelete: (id: number) => void
}

function EditingDateButtonBox({ dateId, handleEdit, handleDelete }: EditingDateButtonBoxProps) {
  return (
    <div className={styles.editingBtnBox}>
      <button type='button' onClick={() => handleEdit(dateId)}>
        수정
      </button>
      <button type='button' className={styles.deleteDateBtn} onClick={() => handleDelete(dateId)}>
        삭제
      </button>
    </div>
  )
}

export default EditingDateButtonBox
